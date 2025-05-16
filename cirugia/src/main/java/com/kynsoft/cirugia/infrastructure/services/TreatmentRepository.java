package com.kynsoft.cirugia.infrastructure.services;

import com.kynsoft.cirugia.application.query.treatment.getbyid.TreatmentResponse;
import com.kynsoft.cirugia.domain.dto.Treatment;
import com.kynsoft.cirugia.domain.service.ITreatmentRepository;
import com.kynsoft.cirugia.infrastructure.entities.TreatmentEntity;
import com.kynsoft.cirugia.infrastructure.repository.command.TreatmentWriteRepository;
import com.kynsoft.cirugia.infrastructure.repository.query.TreatmentReadRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.NoResultException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.ArrayList;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
@Transactional
public class TreatmentRepository implements ITreatmentRepository {

    private final TreatmentWriteRepository treatmentWriteRepository;
    private final TreatmentReadRepository treatmentReadRepository;

    public TreatmentRepository(TreatmentWriteRepository treatmentWriteRepository, TreatmentReadRepository treatmentReadRepository) {
        this.treatmentWriteRepository = treatmentWriteRepository;
        this.treatmentReadRepository = treatmentReadRepository;
    }

    @Override
    public Treatment create(Treatment treatment) {
        if (treatment.getId() == null) {
            treatment.setId(UUID.randomUUID());
            treatment.setCreatedAt(LocalDateTime.now());
        }
        treatment.setUpdatedAt(LocalDateTime.now());
        
        TreatmentEntity entity = mapToEntity(treatment);
        treatmentWriteRepository.save(entity);
        treatmentWriteRepository.flush();
        return mapToDto(entity);
    }

    @Override
    public Optional<Treatment> findById(String id) {
        try {
            Optional<TreatmentEntity> entity = treatmentReadRepository.findById(UUID.fromString(id));
            return Optional.ofNullable(entity.get()).map(this::mapToDto);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Treatment> findBySurgeryId(UUID surgeryId) {
        try {
            List<TreatmentEntity> entities = treatmentReadRepository.findBySurgeryId(surgeryId);
            return entities.stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());
        } catch (NoResultException e) {
            return List.of();
        }
    }

    @Override
    public void deleteById(String id) {
        Optional<TreatmentEntity> entity = treatmentWriteRepository.findById(UUID.fromString(id));
        if (entity.isPresent()) {
            treatmentWriteRepository.delete(entity.get());
        }
    }
    
    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<TreatmentEntity> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<TreatmentEntity> data = treatmentReadRepository.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }
    
    private PaginatedResponse getPaginatedResponse(Page<TreatmentEntity> data) {
        List<TreatmentResponse> treatments = new ArrayList<>();
        for (TreatmentEntity entity : data.getContent()) {
            treatments.add(new TreatmentResponse(mapToDto(entity)));
        }
        return new PaginatedResponse(
            treatments, 
            data.getTotalPages(), 
            data.getNumberOfElements(),
            data.getTotalElements(), 
            data.getSize(), 
            data.getNumber()
        );
    }

    private TreatmentEntity mapToEntity(Treatment dto) {
        TreatmentEntity entity = new TreatmentEntity();
        entity.setId(dto.getId());
        entity.setSurgeryId(dto.getSurgeryId());
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setQuantity(dto.getQuantity());
        entity.setMedicineUnit(dto.getMedicineUnit());
        entity.setStatus(dto.getStatus());
        entity.setProcess(dto.getProcess());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setUpdatedBy(dto.getUpdatedBy());
        entity.setPatientId(dto.getPatientId());
        return entity;
    }

    private Treatment mapToDto(TreatmentEntity entity) {
        return Treatment.builder()
                .id(entity.getId())
                .surgeryId(entity.getSurgeryId())
                .code(entity.getCode())
                .name(entity.getName())
                .description(entity.getDescription())
                .quantity(entity.getQuantity())
                .medicineUnit(entity.getMedicineUnit())
                .status(entity.getStatus())
                .process(entity.getProcess())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }
}