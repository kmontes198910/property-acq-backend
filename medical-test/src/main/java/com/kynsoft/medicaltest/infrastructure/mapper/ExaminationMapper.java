package com.kynsoft.medicaltest.infrastructure.mapper;

import com.kynsoft.medicaltest.application.dto.ExaminationDto;
import com.kynsoft.medicaltest.domain.entity.Examination;
import com.kynsoft.medicaltest.infrastructure.entities.ExaminationEntity;
import com.kynsoft.medicaltest.infrastructure.entities.ExaminationOrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.UUID;

/**
 * Mapper para convertir entre Examination (dominio), ExaminationEntity (JPA) y ExaminationDto
 */
@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ExaminationMapper {
    
    /**
     * Convierte entidad de dominio a entidad JPA
     */
    @Mapping(target = "order", source = "orderId", qualifiedByName = "orderIdToEntity")
    ExaminationEntity toEntity(Examination domain);
    
    /**
     * Convierte entidad JPA a entidad de dominio
     */
    @Mapping(target = "orderId", source = "order", qualifiedByName = "entityToOrderId")
    Examination toDomain(ExaminationEntity entity);
    
    /**
     * Convierte entidad de dominio a DTO
     */
    ExaminationDto toDto(Examination domain);
    
    /**
     * Convierte DTO a entidad de dominio
     */
    Examination toDomain(ExaminationDto dto);
    
    /**
     * Convierte lista de entidades de dominio a lista de DTOs
     */
    List<ExaminationDto> toDtoList(List<Examination> domainList);
    
    /**
     * Convierte lista de entidades JPA a lista de entidades de dominio
     */
    List<Examination> toDomainList(List<ExaminationEntity> entityList);
    
    /**
     * Método auxiliar para obtener el ID de la orden a partir de la entidad
     */
    @Named("entityToOrderId")
    default UUID entityToOrderId(ExaminationOrderEntity entity) {
        return entity != null ? entity.getId() : null;
    }
    
    /**
     * Método auxiliar para crear una referencia a la entidad ExaminationOrderEntity
     */
    @Named("orderIdToEntity")
    default ExaminationOrderEntity orderIdToEntity(UUID id) {
        if (id == null) {
            return null;
        }
        ExaminationOrderEntity orderEntity = new ExaminationOrderEntity();
        orderEntity.setId(id);
        return orderEntity;
    }
}
