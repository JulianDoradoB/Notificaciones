package com.example.notificaciones.infraestructure.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "notificaciones")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private String asunto;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String mensaje;

    @Column(name = "fecha_envio", nullable = false, updatable = false)
    private Timestamp fechaEnvio;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private String canal;

    @Column(name = "referencia_servicio")
    private String referenciaServicio;

    @Column(name = "detalles_adicionales", columnDefinition = "TEXT")
    private String detallesAdicionales;

    public Notificacion() {
        this.fechaEnvio = new Timestamp(System.currentTimeMillis());
    }

    public Notificacion(Long clienteId, String tipo, String asunto, String mensaje, String estado, String canal, String referenciaServicio, String detallesAdicionales) {
        this.clienteId = clienteId;
        this.tipo = tipo;
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.fechaEnvio = new Timestamp(System.currentTimeMillis());
        this.estado = estado;
        this.canal = canal;
        this.referenciaServicio = referenciaServicio;
        this.detallesAdicionales = detallesAdicionales;
    }

    // Getters y setters
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