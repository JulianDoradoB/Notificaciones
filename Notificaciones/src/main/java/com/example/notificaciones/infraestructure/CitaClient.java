package com.example.notificaciones.infraestructure;

import com.example.notificaciones.infraestructure.dto.CitaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "citas-client", url = "http://localhost:8097")
public interface CitaClient {

    @GetMapping("/citas/{id}")
    CitaDTO obtenerCita(@PathVariable("id") Long id);

    // Agrega este método para actualizar el estado de una cita (por ejemplo usando PATCH)
    @PutMapping("/citas/{id}/estado")
    void actualizarEstado(@PathVariable("id") Long id, @RequestParam("estado") String estado);
}
