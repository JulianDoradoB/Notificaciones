// src/main/java/com/example/notificaciones/domain/dto/NotificacionDTO.java
package com.example.notificaciones.domain.dto;

import java.time.LocalDateTime;

public class NotificacionDTO {

    private Long id;
    private Long clienteId;
    private String tipo;
    private String asunto; // Correcto aquí
    private String mensaje;
    private LocalDateTime fechaEnvio;
    private String estado;
    private String canal;
    private String referenciaServicio;
    private String detallesAdicionales;

    private String emailDestinatario;


    public NotificacionDTO() {

    }


    public NotificacionDTO(Long id, Long clienteId, String tipo, String asunto, String mensaje,
                           LocalDateTime fechaEnvio, String estado, String canal,
                           String referenciaServicio, String detallesAdicionales, String emailDestinatario) {
        this.id = id;
        this.clienteId = clienteId;
        this.tipo = tipo;
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.fechaEnvio = fechaEnvio;
        this.estado = estado;
        this.canal = canal;
        this.referenciaServicio = referenciaServicio;
        this.detallesAdicionales = detallesAdicionales;
        this.emailDestinatario = emailDestinatario;
    }

    // --- Getters y Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public String getReferenciaServicio() {
        return referenciaServicio;
    }

    public void setReferenciaServicio(String referenciaServicio) {
        this.referenciaServicio = referenciaServicio;
    }

    public String getDetallesAdicionales() {
        return detallesAdicionales;
    }

    public void setDetallesAdicionales(String detallesAdicionales) {
        this.detallesAdicionales = detallesAdicionales;
    }

    public String getEmailDestinatario() {
        return emailDestinatario;
    }

    public void setEmailDestinatario(String emailDestinatario) {
        this.emailDestinatario = emailDestinatario;
    }

    @Override
    public String toString() {
        return "NotificacionDTO{" +
                "id=" + id +
                ", clienteId=" + clienteId +
                ", tipo='" + tipo + '\'' +
                ", asunto='" + asunto + '\'' + // <-- ¡CORREGIDO! ahora dice "asunto" y tiene la coma inicial
                ", mensaje='" + mensaje + '\'' +
                ", fechaEnvio=" + fechaEnvio +
                ", estado='" + estado + '\'' +
                ", canal='" + canal + '\'' +
                ", referenciaServicio='" + referenciaServicio + '\'' +
                ", detallesAdicionales='" + detallesAdicionales + '\'' +
                ", emailDestinatario='" + emailDestinatario + '\'' +
                '}';
    }
}