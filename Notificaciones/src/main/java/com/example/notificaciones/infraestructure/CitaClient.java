package com.example.notificaciones.infraestructure;

import com.example.notificaciones.infraestructure.dto.CitaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "citas-client", url = "http://localhost:8091") // Cambia puerto si es otro
public interface CitaClient {

    @GetMapping("/citas/{id}")
    CitaDTO obtenerCita(@PathVariable("id") Long id);
}
