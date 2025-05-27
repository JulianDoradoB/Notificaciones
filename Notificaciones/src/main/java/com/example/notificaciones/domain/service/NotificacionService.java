package com.example.notificaciones.domain.service;

import com.example.notificaciones.domain.dto.NotificacionDTO;
import com.example.notificaciones.domain.repository.INotificacion;
import com.example.notificaciones.infraestructure.entity.Notificacion;
import com.example.notificaciones.infraestructure.mapper.NotificacionMapper;
import com.example.notificaciones.infraestructure.CitaClient;
import feign.FeignException; // Importar FeignException
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime; // Asegúrate de importar LocalDateTime
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificacionService {

    private static final Logger log = LoggerFactory.getLogger(NotificacionService.class);

    private final INotificacion notificacionRepo;
    private final NotificacionMapper notificacionMapper;
    private final EmailService emailService;
    private final CitaClient citaClient;

    public NotificacionService(INotificacion notificacionRepo,
                               NotificacionMapper notificacionMapper,
                               EmailService emailService,
                               CitaClient citaClient) {
        this.notificacionRepo = notificacionRepo;
        this.notificacionMapper = notificacionMapper;
        this.emailService = emailService;
        this.citaClient = citaClient;
    }

    public List<NotificacionDTO> getAll() {
        return notificacionRepo.getAll();
    }

    public Optional<NotificacionDTO> getById(Long id) {
        return notificacionRepo.getById(id);
    }

    public List<NotificacionDTO> getByClienteId(Long clienteId) {
        return notificacionRepo.getByClienteId(clienteId);
    }

    @Transactional
    public NotificacionDTO save(NotificacionDTO dto) {
        log.info("DEBUG (Notificaciones - Servicio): Inicia método save para NotificacionDTO: {}", dto.toString());

        // 1. Establecer fecha de envío si es nula
        if (dto.getFechaEnvio() == null) {
            dto.setFechaEnvio(LocalDateTime.now());
        }

        // 2. Establecer estado inicial si es nulo (PENDIENTE antes de intentar enviar)
        if (dto.getEstado() == null || dto.getEstado().isEmpty()) {
            dto.setEstado("PENDIENTE");
        }

        // 3. Manejo del emailDestinatario: Asignar un valor por defecto si es nulo (para evitar 400 Bad Request en el controlador)
        if (dto.getEmailDestinatario() == null || dto.getEmailDestinatario().isEmpty()) {
            dto.setEmailDestinatario("nodisponible@example.com");
            log.warn("ADVERTENCIA (Notificaciones - Servicio): emailDestinatario es nulo o vacío. Asignando valor por defecto.");
        }

        // Mapear DTO a Entity y guardar inicialmente con estado PENDIENTE
        Notificacion notificacion = notificacionMapper.toEntity(dto);
        Notificacion savedNotificacion = notificacionRepo.saveEntity(notificacion);
        NotificacionDTO savedDto = notificacionMapper.toDto(savedNotificacion);
        log.info("DEBUG (Notificaciones - Servicio): Notificación guardada en DB con ID: {}", savedNotificacion.getId());

        String estadoFinalNotificacion = "PENDIENTE"; // Estado por defecto si no es email o falla

        // 4. Enviar correo si el canal es EMAIL y hay destinatario válido
        if ("EMAIL".equalsIgnoreCase(dto.getCanal()) &&
            dto.getEmailDestinatario() != null &&
            !dto.getEmailDestinatario().isEmpty() &&
            !"nodisponible@example.com".equals(dto.getEmailDestinatario())) { // No intentar enviar a default
            try {
                boolean enviadoExitosamente = emailService.enviarCorreo(
                        dto.getEmailDestinatario(),
                        dto.getAsunto() != null ? dto.getAsunto() : "Nueva notificación",
                        dto.getMensaje() != null ? dto.getMensaje() : ""
                );
                if (enviadoExitosamente) {
                    estadoFinalNotificacion = "ENVIADA";
                    log.info("📧 Correo enviado exitosamente a {}", dto.getEmailDestinatario());
                } else {
                    estadoFinalNotificacion = "FALLIDA";
                    log.error("❌ Fallo en el envío de correo a {}. Marcando notificación como FALLIDA.", dto.getEmailDestinatario());
                    savedNotificacion.setDetallesAdicionales(
                        (savedNotificacion.getDetallesAdicionales() != null ? savedNotificacion.getDetallesAdicionales() + "; " : "") +
                        "Fallo al enviar correo por EmailService (retornó false)."
                    );
                }
            } catch (Exception e) {
                estadoFinalNotificacion = "FALLIDA";
                log.error("❌ Excepción al enviar correo a {}: {}", dto.getEmailDestinatario(), e.getMessage(), e);
                savedNotificacion.setDetallesAdicionales(
                    (savedNotificacion.getDetallesAdicionales() != null ? savedNotificacion.getDetallesAdicionales() + "; " : "") +
                    "Excepción durante el envío del correo: " + e.getMessage()
                );
            }
        } else {
            log.info("La notificación {} no es de tipo EMAIL, no tiene destinatario o usa un destinatario por defecto. No se intentó enviar correo.", savedNotificacion.getId());
            // Si no es email, se queda en el estado inicial (PENDIENTE) o se marca como "NO_APLICA_EMAIL"
            // Por simplicidad, si no se envió email, no cambiamos su estado basado en "envío de email".
        }

        // Actualizar estado de la notificación después del intento de envío de correo
        if (!savedNotificacion.getEstado().equals(estadoFinalNotificacion)) {
            savedNotificacion.setEstado(estadoFinalNotificacion);
            savedNotificacion = notificacionRepo.saveEntity(savedNotificacion); // Guarda el estado actualizado
            savedDto = notificacionMapper.toDto(savedNotificacion); // Actualiza el DTO de retorno
            log.info("DEBUG (Notificaciones - Servicio): Estado de notificación {} actualizado a {}", savedNotificacion.getId(), estadoFinalNotificacion);
        }

        // 5. Integración con servicio de Citas (para actualizar el estado de la cita)
        // Este bloque se ejecuta independientemente del éxito del envío del correo.
        if (dto.getReferenciaServicio() != null && dto.getReferenciaServicio().startsWith("CitaService:")) {
            try {
                String[] partes = dto.getReferenciaServicio().split(":");
                if (partes.length == 2) {
                    Long citaId = Long.parseLong(partes[1]);
                    // Se decide el estado que se enviará a Citas. Podría ser el estado final de la notificación
                    String nuevoEstadoCita = savedDto.getEstado() != null ? savedDto.getEstado() : "NOTIFICACION_PROCESADA";

                    log.info("DEBUG (Notificaciones - Servicio): Intentando actualizar estado de cita {} a '{}' en microservicio de Citas.", citaId, nuevoEstadoCita);
                    citaClient.actualizarEstado(citaId, nuevoEstadoCita); // Llama al Feign Client
                    log.info("📅 Estado de cita {} actualizado a '{}' por notificación.", citaId, nuevoEstadoCita);
                } else {
                    log.warn("ADVERTENCIA (Notificaciones - Servicio): Formato de referenciaServicio inválido: {}. No se intentará actualizar la cita.", dto.getReferenciaServicio());
                    savedNotificacion.setDetallesAdicionales(
                        (savedNotificacion.getDetallesAdicionales() != null ? savedNotificacion.getDetallesAdicionales() + "; " : "") +
                        "Formato de referenciaServicio inválido para actualizar cita."
                    );
                    notificacionRepo.saveEntity(savedNotificacion); // Guarda con detalles de error
                    savedDto = notificacionMapper.toDto(savedNotificacion);
                }
            } catch (NumberFormatException e) {
                log.error("❌ ERROR (Notificaciones - Servicio): referenciaServicio no es un ID de cita válido (no numérico): {}. Error: {}", dto.getReferenciaServicio(), e.getMessage(), e);
                savedNotificacion.setDetallesAdicionales(
                    (savedNotificacion.getDetallesAdicionales() != null ? savedNotificacion.getDetallesAdicionales() + "; " : "") +
                    "ID de cita no numérico en referenciaServicio."
                );
                notificacionRepo.saveEntity(savedNotificacion); // Guarda con detalles de error
                savedDto = notificacionMapper.toDto(savedNotificacion);
            } catch (FeignException e) { // Captura específicamente errores de Feign (como 404)
                log.error("❌ ERROR (Notificaciones - Servicio): Feign al actualizar estado de cita {}: HTTP Status {} - {}", dto.getReferenciaServicio(), e.status(), e.getMessage(), e);
                savedNotificacion.setDetallesAdicionales(
                    (savedNotificacion.getDetallesAdicionales() != null ? savedNotificacion.getDetallesAdicionales() + "; " : "") +
                    "Fallo Feign al actualizar estado de cita: " + e.status() + " - " + e.getMessage()
                );
                notificacionRepo.saveEntity(savedNotificacion); // Guarda con detalles de error
                savedDto = notificacionMapper.toDto(savedNotificacion);
                // No relanzamos aquí, solo registramos y la notificación se guarda con el error
            } catch (Exception e) {
                log.error("❌ ERROR (Notificaciones - Servicio): Error inesperado al actualizar estado de cita desde notificación (citaId: {}): {}", dto.getReferenciaServicio(), e.getMessage(), e);
                savedNotificacion.setDetallesAdicionales(
                    (savedNotificacion.getDetallesAdicionales() != null ? savedNotificacion.getDetallesAdicionales() + "; " : "") +
                    "Excepción inesperada al actualizar estado de cita: " + e.getMessage()
                );
                notificacionRepo.saveEntity(savedNotificacion); // Guarda con detalles de error
                savedDto = notificacionMapper.toDto(savedNotificacion);
            }
        }

        log.info("DEBUG (Notificaciones - Servicio): Fin método save para NotificacionDTO ID: {}", savedDto.getId());
        return savedDto;
    }

    /**
     * Guarda una lista de notificaciones, procesando cada una individualmente.
     * Si la duplicación viene del cliente, este método procesará cada una.
     * No hay @Retryable aquí que cause duplicación en Notificaciones.
     */
    public List<NotificacionDTO> saveAll(List<NotificacionDTO> dtos) {
        log.info("DEBUG (Notificaciones - Servicio): Inicia método saveAll para {} notificaciones.", dtos.size());
        // Simplemente mapea y guarda cada DTO, la duplicación DEBE venir de la entrada
        List<NotificacionDTO> results = dtos.stream()
                .map(this::save) // Reutiliza la lógica de save individualmente
                .collect(Collectors.toList());
        log.info("DEBUG (Notificaciones - Servicio): Finaliza método saveAll. Procesadas: {}", results.size());
        return results;
    }

    // ... (Método delete, etc. - ya lo tenías bien)
    public boolean delete(Long id) {
        try {
            notificacionRepo.delete(id); // Asumiendo que `delete` en INotificacion toma el ID
            log.info("🗑️ Notificación {} eliminada", id);
            return true;
        } catch (Exception e) {
            log.error("❌ Error al eliminar notificación {}: {}", id, e.getMessage(), e);
            return false;
        }
    }
}