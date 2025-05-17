package com.kynsoft.medicaltest.infrastructure.mapper;

import com.kynsoft.medicaltest.application.dto.ExaminationOrderDto;
import com.kynsoft.medicaltest.domain.entity.ExaminationOrder;
import com.kynsoft.medicaltest.infrastructure.entities.ExaminationOrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper para convertir entre ExaminationOrder (dominio), ExaminationOrderEntity (JPA) y ExaminationOrderDto
 */
@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = {ExaminationMapper.class}
)
public interface ExaminationOrderMapper {
    
    /**
     * Convierte entidad de dominio a entidad JPA
     */
    @Mapping(target = "examinations", ignore = true)
    ExaminationOrderEntity toEntity(ExaminationOrder domain);
    
    /**
     * Convierte entidad JPA a entidad de dominio
     */
    @Mapping(target = "examinations", source = "examinations")
    ExaminationOrder toDomain(ExaminationOrderEntity entity);
    
    /**
     * Convierte entidad de dominio a DTO
     */
    ExaminationOrderDto toDto(ExaminationOrder domain);
    
    /**
     * Convierte DTO a entidad de dominio
     */
    ExaminationOrder toDomain(ExaminationOrderDto dto);
    
    /**
     * Convierte lista de entidades de dominio a lista de DTOs
     */
    List<ExaminationOrderDto> toDtoList(List<ExaminationOrder> domainList);
    
    /**
     * Convierte lista de entidades JPA a lista de entidades de dominio
     */
    List<ExaminationOrder> toDomainList(List<ExaminationOrderEntity> entityList);
}
