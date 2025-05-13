package com.example.notificaciones.infraestructure;

import com.example.notificaciones.infraestructure.dto.PagoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "pagos-client", url = "http://localhost:8092") // Cambia puerto si es otro
public interface PagoClient {

    @GetMapping("/pagos/{id}")
    PagoDTO obtenerPago(@PathVariable("id") Long id);
}
