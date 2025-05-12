package com.example.notificaciones.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.notificaciones.domain.dto.NotificacionDTO;
import com.example.notificaciones.domain.service.NotificacionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @PostMapping("/enviar")
    public ResponseEntity<NotificacionDTO> enviarNotificacion(@RequestBody NotificacionDTO notificacionDTO) {
        NotificacionDTO nuevaNotificacion = notificacionService.save(notificacionDTO);
        return new ResponseEntity<>(nuevaNotificacion, HttpStatus.CREATED);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<NotificacionDTO>> getNotificacionesPorCliente(@PathVariable("clienteId") Long clienteId) {
        List<NotificacionDTO> notificaciones = notificacionService.getByClienteId(clienteId);
        return new ResponseEntity<>(notificaciones, HttpStatus.OK);
    }

    @PostMapping("/masivas")
    public ResponseEntity<List<NotificacionDTO>> enviarNotificacionesMasivas(@RequestBody List<NotificacionDTO> notificacionesDTO) {
        List<NotificacionDTO> notificacionesEnviadas = notificacionService.saveAll(notificacionesDTO);
        return new ResponseEntity<>(notificacionesEnviadas, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificacionDTO> getNotificacion(@PathVariable("id") Long id) {
        Optional<NotificacionDTO> notificacion = notificacionService.getById(id);
        return notificacion.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotificacion(@PathVariable("id") Long id) {
        if (notificacionService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint avanzado (ejemplo)
    @GetMapping("/cliente/{clienteId}/estadisticas")
    public ResponseEntity<String> getEstadisticasCliente(@PathVariable("clienteId") Long clienteId) {
        // Aquí iría la lógica para calcular y retornar las estadísticas
        return new ResponseEntity<>("Estadísticas para el cliente " + clienteId, HttpStatus.OK);
    }
}