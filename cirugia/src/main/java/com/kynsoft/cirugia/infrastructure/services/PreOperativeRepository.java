package com.kynsoft.cirugia.infrastructure.services;

import com.kynsoft.cirugia.domain.dto.PreOperative;
import com.kynsoft.cirugia.domain.service.IPreOperativeRepository;
import com.kynsoft.cirugia.infrastructure.entities.PreOperativeEntity;
import com.kynsoft.cirugia.infrastructure.repository.command.PreOperativetWriteRepository;
import com.kynsoft.cirugia.infrastructure.repository.query.PreOperativeReadRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public class PreOperativeRepository implements IPreOperativeRepository {

    private final PreOperativetWriteRepository preOperativetWriteRepository;
    private final PreOperativeReadRepository preOperativeReadRepository;

    public PreOperativeRepository(PreOperativetWriteRepository entityManager, PreOperativeReadRepository preOperativeReadRepository) {
        this.preOperativetWriteRepository = entityManager;
        this.preOperativeReadRepository = preOperativeReadRepository;
    }

    @Override
    public PreOperative create(PreOperative preOperative) {
        if (preOperative.getId() == null) {
            preOperative.setId(UUID.randomUUID());
            preOperative.setCreatedAt(LocalDateTime.now());
        }
        preOperative.setUpdatedAt(LocalDateTime.now());
        
        PreOperativeEntity entity = mapToEntity(preOperative);
        preOperativetWriteRepository.save(entity);
        preOperativetWriteRepository.flush();
        return mapToDto(entity);
    }

    @Override
    public Optional<PreOperative> findById(String id) {
        try {
            Optional<PreOperativeEntity> entity = preOperativeReadRepository.findById( UUID.fromString(id));
            return Optional.ofNullable(entity.get()).map(this::mapToDto);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<PreOperative> findBySurgeryId(UUID surgeryId) {
        try {
            Optional<PreOperativeEntity> entity = preOperativeReadRepository.findBySurgeryId(surgeryId);

            return Optional.ofNullable(entity.get()).map(this::mapToDto);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(String id) {
        Optional<PreOperativeEntity> entity = preOperativetWriteRepository.findById(UUID.fromString(id));
        if (entity != null) {
            preOperativetWriteRepository.delete(entity.get());
        }
    }

    private PreOperativeEntity mapToEntity(PreOperative dto) {
        return PreOperativeEntity.builder()
                .id(dto.getId())
                .surgeryId(dto.getSurgeryId())
                .admissionReason(dto.getAdmissionReason())
                .currentDiseaseHistory(dto.getCurrentDiseaseHistory())
                .physicalExamination(dto.getPhysicalExamination())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .createdBy(dto.getCreatedBy())
                .updatedBy(dto.getUpdatedBy())
                .build();
    }

    private PreOperative mapToDto(PreOperativeEntity entity) {
        return PreOperative.builder()
                .id(entity.getId())
                .surgeryId(entity.getSurgeryId())
                .admissionReason(entity.getAdmissionReason())
                .currentDiseaseHistory(entity.getCurrentDiseaseHistory())
                .physicalExamination(entity.getPhysicalExamination())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }
}