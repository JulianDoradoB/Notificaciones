package com.example.notificaciones.infraestructure;

import com.example.notificaciones.infraestructure.dto.PagoDTO; // Asegúrate de que este DTO contenga todos los campos de Pago (id, citaId, monto, etc.)
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping; // Importar PutMapping
import org.springframework.web.bind.annotation.RequestBody; // Importar RequestBody

@FeignClient(name = "pagos-client", url = "http://localhost:8092")
public interface PagoClient {

    @GetMapping("/pagos/{id}")
    PagoDTO obtenerPago(@PathVariable("id") Long id);

    /**
     * Método para actualizar un pago existente en el microservicio de Pagos.
     * Invoca el endpoint PUT /api/pagos/{id} del microservicio de Pagos.
     *
     * @param id El ID del pago a actualizar.
     * @param pagoDTO El objeto PagoDTO con los datos actualizados.
     * @return ResponseEntity<PagoDTO> con el pago actualizado si la operación es exitosa.
     */
    @PutMapping("/pagos/{id}") // El endpoint completo del microservicio de Pagos
    ResponseEntity<PagoDTO> actualizarPago(@PathVariable("id") Long id, @RequestBody PagoDTO pagoDTO);

}