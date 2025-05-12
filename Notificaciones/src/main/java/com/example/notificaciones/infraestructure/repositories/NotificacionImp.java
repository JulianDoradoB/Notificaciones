package com.example.notificaciones.infraestructure.repositories;

import com.example.notificaciones.domain.dto.NotificacionDTO;
import com.example.notificaciones.domain.repository.INotificacion;
import com.example.notificaciones.infraestructure.crud.NotificacionRepository;
import com.example.notificaciones.infraestructure.entity.Notificacion;
import com.example.notificaciones.infraestructure.mapper.NotificacionMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class NotificacionImp implements INotificacion {

    private final NotificacionRepository notificacionRepository;
    private final NotificacionMapper notificacionMapper;

    public NotificacionImp(NotificacionRepository notificacionRepository, NotificacionMapper notificacionMapper) {
        this.notificacionRepository = notificacionRepository;
        this.notificacionMapper = notificacionMapper;
    }

    @Override
    public List<NotificacionDTO> getAll() {
        return notificacionRepository.findAll()
                .stream()
                .map(notificacionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<NotificacionDTO> getById(Long id) {
        return notificacionRepository.findById(id)
                .map(notificacionMapper::toDto);
    }

    @Override
    public List<NotificacionDTO> getByClienteId(Long clienteId) {
        return notificacionRepository.findByClienteId(clienteId)
                .stream()
                .map(notificacionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public NotificacionDTO save(NotificacionDTO notificacionDTO) {
        Notificacion notificacion = notificacionMapper.toEntity(notificacionDTO);
        Notificacion savedNotificacion = notificacionRepository.save(notificacion);
        return notificacionMapper.toDto(savedNotificacion);
    }

    @Override
    public List<NotificacionDTO> saveAll(List<NotificacionDTO> notificacionesDTO) {
        List<Notificacion> notificaciones = notificacionesDTO.stream()
                .map(notificacionMapper::toEntity)
                .collect(Collectors.toList());
        List<Notificacion> savedNotificaciones = notificacionRepository.saveAll(notificaciones);
        return savedNotificaciones.stream()
                .map(notificacionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        notificacionRepository.deleteById(id);
    }

    @Override
    public Notificacion saveEntity(Notificacion notificacion) {
        return notificacionRepository.save(notificacion);
    }

    // >>>>> AÑADE ESTA IMPLEMENTACIÓN <<<<<
    @Override
    public List<Notificacion> saveAllEntities(List<Notificacion> notificaciones) {
        return notificacionRepository.saveAll(notificaciones);
    }
}