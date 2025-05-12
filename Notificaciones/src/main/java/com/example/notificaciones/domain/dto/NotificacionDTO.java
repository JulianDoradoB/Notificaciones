package com.example.notificaciones.domain.dto;

import java.sql.Timestamp;

public class NotificacionDTO {

    private Long id;
    private Long clienteId;
    private String tipo;
    private String asunto;
    private String mensaje;
    private Timestamp fechaEnvio;
    private String estado;
    private String canal;
    private String referenciaServicio;
    private String detallesAdicionales;

    // Constructores (con y sin argumentos), getters y setters
    public NotificacionDTO() {
    }

    public NotificacionDTO(Long id, Long clienteId, String tipo, String asunto, String mensaje, Timestamp fechaEnvio, String estado, String canal, String referenciaServicio, String detallesAdicionales) {
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
    }

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

    public Timestamp getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Timestamp fechaEnvio) {
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
}