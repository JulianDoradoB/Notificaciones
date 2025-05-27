package com.example.notificaciones.infraestructure.mapper;

import com.example.notificaciones.domain.dto.NotificacionDTO;
import com.example.notificaciones.infraestructure.entity.Notificacion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface NotificacionMapper {

    NotificacionMapper INSTANCE = Mappers.getMapper(NotificacionMapper.class);

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

    // Métodos de conversión explícitos para manejar tipos incompatibles
    default Timestamp map(LocalDateTime localDateTime) {
        return (localDateTime == null) ? null : Timestamp.valueOf(localDateTime);
    }

    default LocalDateTime map(Timestamp timestamp) {
        return (timestamp == null) ? null : timestamp.toLocalDateTime();
    }
}
