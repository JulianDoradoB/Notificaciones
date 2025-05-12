package com.example.notificaciones.domain.repository;

import com.example.notificaciones.domain.dto.NotificacionDTO;
import com.example.notificaciones.infraestructure.entity.Notificacion;

import java.util.List;
import java.util.Optional;

public interface INotificacion {
    List<NotificacionDTO> getAll();
    Optional<NotificacionDTO> getById(Long id);
    List<NotificacionDTO> getByClienteId(Long clienteId);
    NotificacionDTO save(NotificacionDTO notificacionDTO);
    List<NotificacionDTO> saveAll(List<NotificacionDTO> notificacionesDTO);
    void delete(Long id);

    Notificacion saveEntity(Notificacion notificacion);
    List<Notificacion> saveAllEntities(List<Notificacion> notificaciones);
}