package com.example.notificaciones.controller;

import com.example.notificaciones.domain.dto.NotificacionDTO;
import com.example.notificaciones.domain.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime; // Importar LocalDateTime
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    // ✅ Envía una notificación y correo automáticamente
    @PostMapping("/enviar")
    public ResponseEntity<NotificacionDTO> enviarNotificacion(@RequestBody NotificacionDTO notificacionDTO) {
        // --- INICIO DE LA SOLUCIÓN AL 400 BAD_REQUEST ---
        // 1. Log para depuración para ver el DTO entrante completo
        System.out.println("DEBUG (Notificaciones): Notificación recibida para enviar: " + notificacionDTO.toString());

        // 2. Manejo de campos que pueden venir nulos desde el cliente (Pagos)
        // Establecer fechaEnvio si es nulo (se asume que se envía en el momento de recepción)
        if (notificacionDTO.getFechaEnvio() == null) {
            notificacionDTO.setFechaEnvio(LocalDateTime.now());
            System.out.println("DEBUG (Notificaciones): Fecha de envío establecida a: " + notificacionDTO.getFechaEnvio());
        }

        // Establecer estado inicial si es nulo
        if (notificacionDTO.getEstado() == null || notificacionDTO.getEstado().isEmpty()) {
            notificacionDTO.setEstado("PENDIENTE"); // O "CREADA", "RECIBIDA", etc.
            System.out.println("DEBUG (Notificaciones): Estado establecido a: " + notificacionDTO.getEstado());
        }

        // Manejo del emailDestinatario: Este es el CAMPO CLAVE del 400 actual.
        // Si Pagos no lo envía, no podemos exigir que no sea nulo sin asignarle un valor.
        if (notificacionDTO.getEmailDestinatario() == null || notificacionDTO.getEmailDestinatario().isEmpty()) {
            // Opción A: Devolver BAD_REQUEST si REALMENTE necesitas el email del cliente en Pagos.
            // En este caso, Pagos debería enviar el email.
            // return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            // Opción B: Asignar un valor por defecto o nulo si lo puedes manejar en Notificaciones.
            // Esto es útil si el email puede obtenerse de otra fuente o no es obligatorio en este punto.
            // Por ejemplo, podrías obtenerlo de un servicio de usuarios/clientes usando clienteId.
            System.err.println("ADVERTENCIA (Notificaciones): emailDestinatario es nulo o vacío. Asignando valor por defecto/nulo.");
            notificacionDTO.setEmailDestinatario("nodisponible@example.com"); // O simplemente no hacer nada si tu DB lo permite nulo
        }
        // --- FIN DE LA SOLUCIÓN AL 400 BAD_REQUEST ---

        try {
            NotificacionDTO nuevaNotificacion = notificacionService.save(notificacionDTO);
            System.out.println("DEBUG (Notificaciones): Notificación guardada y enviada al servicio: " + nuevaNotificacion.getId());
            return new ResponseEntity<>(nuevaNotificacion, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("ERROR (Notificaciones): Error al procesar y guardar la notificación: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // En caso de un error interno al guardar/procesar
        }
    }

    // ✅ Obtener todas las notificaciones por ID de cliente
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<NotificacionDTO>> getNotificacionesPorCliente(@PathVariable("clienteId") Long clienteId) {
        List<NotificacionDTO> notificaciones = notificacionService.getByClienteId(clienteId);
        if (notificaciones.isEmpty()) {
            return ResponseEntity.noContent().build(); // Retornar 204 No Content si no hay notificaciones
        }
        return new ResponseEntity<>(notificaciones, HttpStatus.OK);
    }

    // ✅ Envía notificaciones en lote (aún no envía correo, si quieres lo agregamos)
    // Considera si este endpoint necesita validaciones similares a /enviar
    @PostMapping("/masivas")
    public ResponseEntity<List<NotificacionDTO>> enviarNotificacionesMasivas(@RequestBody List<NotificacionDTO> notificacionesDTO) {
        // Podrías iterar sobre la lista y aplicar la misma lógica de manejo de campos
        // y errores para cada notificación si es necesario.
        List<NotificacionDTO> notificacionesEnviadas = notificacionService.saveAll(notificacionesDTO);
        return new ResponseEntity<>(notificacionesEnviadas, HttpStatus.CREATED);
    }

    // ✅ Obtener notificación por ID
    @GetMapping("/{id}")
    public ResponseEntity<NotificacionDTO> getNotificacion(@PathVariable("id") Long id) {
        Optional<NotificacionDTO> notificacion = notificacionService.getById(id);
        return notificacion.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Eliminar una notificación por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotificacion(@PathVariable("id") Long id) {
        if (notificacionService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // (Opcional) Estadísticas para cliente
    @GetMapping("/cliente/{clienteId}/estadisticas")
    public ResponseEntity<String> getEstadisticasCliente(@PathVariable("clienteId") Long clienteId) {
        // Aquí iría la lógica real
        return new ResponseEntity<>("Estadísticas para el cliente " + clienteId, HttpStatus.OK);
    }
}