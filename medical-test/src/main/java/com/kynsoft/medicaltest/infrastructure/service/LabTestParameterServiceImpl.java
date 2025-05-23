package com.kynsoft.medicaltest.infrastructure.service;

import com.kynsoft.medicaltest.domain.dto.LabTestParameterDto;
import com.kynsoft.medicaltest.domain.service.ILabTestParameterService;
import com.kynsoft.medicaltest.infrastructure.entities.LabTestEntity;
import com.kynsoft.medicaltest.infrastructure.entities.LabTestParameterEntity;
import com.kynsoft.medicaltest.infrastructure.repository.command.LabTestParameterWriteRepository;
import com.kynsoft.medicaltest.infrastructure.repository.query.LabTestParameterReadJpaRepository;
import com.kynsoft.medicaltest.infrastructure.repository.query.LabTestReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementación del servicio para gestionar parámetros de exámenes de laboratorio
 */
@Service
@RequiredArgsConstructor
public class LabTestParameterServiceImpl implements ILabTestParameterService {

    private final LabTestParameterReadJpaRepository parameterReadRepository;
    private final LabTestParameterWriteRepository parameterWriteRepository;
    private final LabTestReadRepository labTestReadRepository;

    @Override
    @Transactional(readOnly = true)
    public LabTestParameterDto getById(UUID id) {
        LabTestParameterEntity entity = parameterReadRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Parámetro de examen no encontrado con ID: " + id));
        return mapToDomain(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LabTestParameterDto> getByLabTestId(UUID labTestId) {
        List<LabTestParameterEntity> parameters = parameterReadRepository.findByLabTestId(labTestId);
        return parameters.stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LabTestParameterDto create(LabTestParameterDto dto, UUID userId) {
        LabTestEntity labTest = labTestReadRepository.findById(dto.getLabTestId())
                .orElseThrow(() -> new NoSuchElementException("Examen de laboratorio no encontrado con ID: " + dto.getLabTestId()));

        LabTestParameterEntity entity = mapToEntity(dto);
        entity.setLabTest(labTest);
        entity.setCreatedBy(userId);

        LabTestParameterEntity savedEntity = parameterWriteRepository.save(entity);
        return mapToDomain(savedEntity);
    }

    @Override
    @Transactional
    public LabTestParameterDto update(UUID id, LabTestParameterDto dto, UUID userId) {
        LabTestParameterEntity entity = parameterReadRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Parámetro de examen no encontrado con ID: " + id));

        // Actualizar solo los campos permitidos
        entity.setName(dto.getName());
        entity.setUnit(dto.getUnit());
        entity.setReferenceRangeMin(dto.getReferenceRangeMin());
        entity.setReferenceRangeMax(dto.getReferenceRangeMax());
        entity.setGenderSpecific(dto.getGenderSpecific());
        entity.setUpdatedBy(userId);

        LabTestParameterEntity savedEntity = parameterWriteRepository.save(entity);
        return mapToDomain(savedEntity);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!parameterReadRepository.existsById(id)) {
            throw new NoSuchElementException("Parámetro de examen no encontrado con ID: " + id);
        }
        parameterWriteRepository.deleteById(id);
    }

    /**
     * Mapea una entidad LabTestParameterEntity a un DTO LabTestParameterDto
     *
     * @param entity La entidad a mapear
     * @return El DTO mapeado
     */
    private LabTestParameterDto mapToDomain(LabTestParameterEntity entity) {
        return LabTestParameterDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .unit(entity.getUnit())
                .referenceRangeMin(entity.getReferenceRangeMin())
                .referenceRangeMax(entity.getReferenceRangeMax())
                .genderSpecific(entity.getGenderSpecific())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .labTestId(entity.getLabTest().getId())
                .build();
    }

    /**
     * Mapea un DTO LabTestParameterDto a una entidad LabTestParameterEntity
     *
     * @param dto El DTO a mapear
     * @return La entidad mapeada
     */
    private LabTestParameterEntity mapToEntity(LabTestParameterDto dto) {
        return LabTestParameterEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .unit(dto.getUnit())
                .referenceRangeMin(dto.getReferenceRangeMin())
                .referenceRangeMax(dto.getReferenceRangeMax())
                .genderSpecific(dto.getGenderSpecific())
                .createdBy(dto.getCreatedBy())
                .updatedBy(dto.getUpdatedBy())
                .build();
    }
}
