package com.example.notificaciones.domain.service;

import com.example.notificaciones.domain.dto.NotificacionDTO;
import com.example.notificaciones.domain.repository.INotificacion;
import com.example.notificaciones.infraestructure.entity.Notificacion;
import com.example.notificaciones.infraestructure.mapper.NotificacionMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificacionService {

    private final INotificacion notificacionRepo;
    private final NotificacionMapper notificacionMapper;

    public NotificacionService(INotificacion notificacionRepo, NotificacionMapper notificacionMapper) {
        this.notificacionRepo = notificacionRepo;
        this.notificacionMapper = notificacionMapper;
    }

    public List<NotificacionDTO> getAll() {
        return notificacionRepo.getAll();
    }

    public Optional<NotificacionDTO> getById(Long id) {
        return notificacionRepo.getById(id);
    }

    public List<NotificacionDTO> getByClienteId(Long clienteId) {
        return notificacionRepo.getByClienteId(clienteId);
    }

    public NotificacionDTO save(NotificacionDTO dto) {
        Notificacion notificacion = notificacionMapper.toEntity(dto);
        Notificacion savedNotificacion = notificacionRepo.saveEntity(notificacion);
        return notificacionMapper.toDto(savedNotificacion);
    }

    public List<NotificacionDTO> saveAll(List<NotificacionDTO> dtos) {
        List<Notificacion> notificaciones = dtos.stream()
                .map(notificacionMapper::toEntity)
                .collect(Collectors.toList());

        // >>>>> CAMBIA ESTA LÍNEA <<<<<
        List<Notificacion> savedNotificaciones = notificacionRepo.saveAllEntities(notificaciones);

        return savedNotificaciones.stream()
                .map(notificacionMapper::toDto)
                .collect(Collectors.toList());
    }

    public boolean delete(Long id) {
        notificacionRepo.delete(id);
        return true;
    }
}