package com.example.notificaciones.infraestructure.mapper;

import com.example.notificaciones.domain.dto.NotificacionDTO;
import com.example.notificaciones.infraestructure.entity.Notificacion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificacionMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "clienteId", target = "clienteId")
    @Mapping(source = "tipo", target = "tipo")
    @Mapping(source = "asunto", target = "asunto")
    @Mapping(source = "mensaje", target = "mensaje")
    @Mapping(source = "fechaEnvio", target = "fechaEnvio")
    @Mapping(source = "estado", target = "estado")
    @Mapping(source = "canal", target = "canal")
    @Mapping(source = "referenciaServicio", target = "referenciaServicio")
    @Mapping(source = "detallesAdicionales", target = "detallesAdicionales")
    NotificacionDTO toDto(Notificacion notificacion);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "clienteId", target = "clienteId")
    @Mapping(source = "tipo", target = "tipo")
    @Mapping(source = "asunto", target = "asunto")
    @Mapping(source = "mensaje", target = "mensaje")
    @Mapping(source = "fechaEnvio", target = "fechaEnvio")
    @Mapping(source = "estado", target = "estado")
    @Mapping(source = "canal", target = "canal")
    @Mapping(source = "referenciaServicio", target = "referenciaServicio")
    @Mapping(source = "detallesAdicionales", target = "detallesAdicionales")
    Notificacion toEntity(NotificacionDTO notificacionDTO);
}