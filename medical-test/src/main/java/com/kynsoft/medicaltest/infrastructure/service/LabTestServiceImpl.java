package com.kynsoft.medicaltest.infrastructure.service;

import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.medicaltest.application.query.labtest.getbyid.LabTestResponse;
import com.kynsoft.medicaltest.domain.dto.LabTestDto;
import com.kynsoft.medicaltest.domain.dto.LabTestParameterDto;
import com.kynsoft.medicaltest.domain.service.ILabTestService;
import com.kynsoft.medicaltest.infrastructure.entities.LabTestEntity;
import com.kynsoft.medicaltest.infrastructure.entities.LabTestParameterEntity;
import com.kynsoft.medicaltest.infrastructure.repository.command.LabTestWriteRepository;
import com.kynsoft.medicaltest.infrastructure.repository.query.LabTestReadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementación del servicio para la gestión de exámenes de laboratorio
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LabTestServiceImpl implements ILabTestService {

    private final LabTestWriteRepository labTestWriteRepository;
    private final LabTestReadRepository labTestReadRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<LabTestDto> findById(UUID id) {
        log.info("Buscando examen de laboratorio con ID: {}", id);
        return labTestReadRepository.findById(id).map(this::mapToDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LabTestDto> findByCode(String code) {
        log.info("Buscando examen de laboratorio con código: {}", code);
        return labTestReadRepository.findByCode(code).map(this::mapToDomain);
    }

    // El método findByCategory ha sido removido porque el campo category ya no existe

    @Override
    @Transactional
    public LabTestDto create(LabTestDto labTestDto) {
        log.info("Creando nuevo examen de laboratorio: {}", labTestDto.getName());
        
        // Verificar si ya existe un examen con el mismo código
        if (labTestReadRepository.findByCode(labTestDto.getCode()).isPresent()) {
            throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.ALREADY_EXISTS, new ErrorField("id", "Ya existe un examen con el código: " + labTestDto.getCode())));
        }
        
        // Si no tiene ID, generar uno nuevo
        if (labTestDto.getId() == null) {
            labTestDto.setId(UUID.randomUUID());
        }
        
        // Establecer fechas de creación si no existen
        if (labTestDto.getCreatedAt() == null) {
            LocalDateTime now = LocalDateTime.now();
            labTestDto.setCreatedAt(now);
            labTestDto.setUpdatedAt(now);
        }
        
        LabTestEntity entity = mapToEntity(labTestDto);
        LabTestEntity savedEntity = labTestWriteRepository.save(entity);
        return mapToDomain(savedEntity);
    }

    @Override
    @Transactional
    public LabTestDto update(LabTestDto labTestDto) {
        log.info("Actualizando examen de laboratorio con ID: {}", labTestDto.getId());
        
        // Verificar que el examen exista
        LabTestEntity existingEntity = labTestReadRepository.findById(labTestDto.getId())
                .orElseThrow(() -> new BusinessNotFoundException(
                        new GlobalBusinessException(DomainErrorMessage.BUSINESS_NOT_FOUND, 
                                new ErrorField("id", "Examen de laboratorio no encontrado con ID: " + labTestDto.getId()))));
        
        // Verificar si al cambiar el código no existe otro con el mismo código
        if (!existingEntity.getCode().equals(labTestDto.getCode()) && 
            labTestReadRepository.findByCode(labTestDto.getCode()).isPresent()) {
            throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.OBJECT_NOT_FOUNT, new ErrorField("id", "Elemento no encontrado con el código: " + labTestDto.getCode())));
        }
        
        // Actualizar la fecha de modificación
        labTestDto.setUpdatedAt(LocalDateTime.now());
        labTestDto.setCreatedAt(existingEntity.getCreatedAt());
        
        LabTestEntity entity = mapToEntity(labTestDto);
        LabTestEntity savedEntity = labTestWriteRepository.save(entity);
        return mapToDomain(savedEntity);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        log.info("Eliminando examen de laboratorio con ID: {}", id);
        
        if (!labTestReadRepository.existsById(id)) {
            throw new BusinessNotFoundException(
                new GlobalBusinessException(DomainErrorMessage.BUSINESS_NOT_FOUND, 
                new ErrorField("id", "Examen de laboratorio no encontrado con ID: " + id)));
        }
        
        try {
            labTestWriteRepository.deleteById(id);
        } catch (Exception e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.NOT_DELETE, new ErrorField("id", "Elemento no encontrado con el id: " + id)));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        log.info("Buscando exámenes de laboratorio con criterios de filtrado: {}", filterCriteria);
        GenericSpecificationsBuilder<LabTestEntity> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<LabTestEntity> data = labTestReadRepository.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }
    
    /**
     * Convierte los resultados de página a respuesta paginada
     * 
     * @param data Página de entidades
     * @return Respuesta paginada con DTOs
     */
    private PaginatedResponse getPaginatedResponse(Page<LabTestEntity> data) {
        List<LabTestResponse> labTests = new ArrayList<>();
        for (LabTestEntity entity : data.getContent()) {
            LabTestDto dto = mapToDomain(entity);
            labTests.add(new LabTestResponse(dto));
        }
        return new PaginatedResponse(
            labTests, 
            data.getTotalPages(), 
            data.getNumberOfElements(),
            data.getTotalElements(), 
            data.getSize(), 
            data.getNumber()
        );
    }
    
    /**
     * Mapea una entidad LabTestEntity a un DTO LabTestDto
     * 
     * @param entity La entidad a mapear
     * @return El DTO resultante
     */
    private LabTestDto mapToDomain(LabTestEntity entity) {
        LabTestDto dto = LabTestDto.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .name(entity.getName())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .build();
        
//        // Mapear parámetros si existen
//        if (entity.getParameters() != null && !entity.getParameters().isEmpty()) {
//            dto.setParameters(entity.getParameters().stream()
//                    .map(this::mapParameterToDomain)
//                    .collect(Collectors.toList()));
//        }
        
        return dto;
    }
    
    /**
     * Mapea un DTO LabTestDto a una entidad LabTestEntity
     * 
     * @param dto El DTO a mapear
     * @return La entidad resultante
     */
    private LabTestEntity mapToEntity(LabTestDto dto) {
        LabTestEntity entity = LabTestEntity.builder()
                .id(dto.getId())
                .code(dto.getCode())
                .name(dto.getName())
                .description(dto.getDescription())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .createdBy(dto.getCreatedBy())
                .updatedBy(dto.getUpdatedBy())
                .parameters(new ArrayList<>())
                .build();
        
        // Mapear parámetros si existen
        if (dto.getParameters() != null && !dto.getParameters().isEmpty()) {
            List<LabTestParameterEntity> parameters = dto.getParameters().stream()
                    .map(paramDto -> mapParameterToEntity(paramDto, entity))
                    .collect(Collectors.toList());
            entity.setParameters(parameters);
        }
        
        return entity;
    }
    
    /**
     * Mapea una entidad LabTestParameterEntity a un DTO LabTestParameterDto
     * 
     * @param entity La entidad parámetro a mapear
     * @return El DTO resultante
     */
    private LabTestParameterDto mapParameterToDomain(LabTestParameterEntity entity) {
        return LabTestParameterDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }
    
    /**
     * Mapea un DTO LabTestParameterDto a una entidad LabTestParameterEntity
     * 
     * @param dto El DTO parámetro a mapear
     * @param labTest La entidad laboratorio relacionada
     * @return La entidad resultante
     */
    private LabTestParameterEntity mapParameterToEntity(LabTestParameterDto dto, LabTestEntity labTest) {
        return LabTestParameterEntity.builder()
                .id(dto.getId() != null ? dto.getId() : UUID.randomUUID())
                .labTest(labTest)
                .name(dto.getName())
                .createdAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now())
                .updatedAt(dto.getUpdatedAt() != null ? dto.getUpdatedAt() : LocalDateTime.now())
                .createdBy(dto.getCreatedBy())
                .updatedBy(dto.getUpdatedBy())
                .build();
    }
}
