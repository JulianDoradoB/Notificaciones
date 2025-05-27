package com.example.notificaciones.infraestructure;

import com.example.notificaciones.infraestructure.dto.MedicoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "medicos-client", url = "http://localhost:8093") 
public interface MedicoClient {

    @GetMapping("/medicos/{id}")
    MedicoDTO obtenerMedico(@PathVariable("id") Long id);
}
