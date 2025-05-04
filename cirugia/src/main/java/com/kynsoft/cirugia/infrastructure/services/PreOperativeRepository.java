package com.kynsoft.cirugia.infrastructure.services;

import com.kynsoft.cirugia.domain.dto.PreOperative;
import com.kynsoft.cirugia.domain.service.IPreOperativeRepository;
import com.kynsoft.cirugia.infrastructure.entities.PreOperativeEntity;
import com.kynsoft.cirugia.infrastructure.repository.command.PreOperativetWriteRepository;
import com.kynsoft.cirugia.infrastructure.repository.query.PreOperativeReadRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.NoResultException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public class PreOperativeRepository implements IPreOperativeRepository {

    private final PreOperativetWriteRepository preOperativeWriteRepository;
    private final PreOperativeReadRepository preOperativeReadRepository;

    public PreOperativeRepository(PreOperativetWriteRepository writeRepository, PreOperativeReadRepository readRepository) {
        this.preOperativeWriteRepository = writeRepository;
        this.preOperativeReadRepository = readRepository;
    }

    @Override
    public PreOperative create(PreOperative preOperative) {
        if (preOperative.getId() == null) {
            preOperative.setId(UUID.randomUUID());
            preOperative.setCreatedAt(LocalDateTime.now());
        }
        preOperative.setUpdatedAt(LocalDateTime.now());
        
        PreOperativeEntity entity = mapToEntity(preOperative);
        return mapToDto(preOperativeWriteRepository.save(entity));
    }

    @Override
    public Optional<PreOperative> findById(String id) {
        try {
            return preOperativeReadRepository.findById(UUID.fromString(id))
                   .map(this::mapToDto);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<PreOperative> findBySurgeryId(UUID surgeryId) {
        return preOperativeReadRepository.findBySurgeryId(surgeryId)
               .map(this::mapToDto);
    }

    @Override
    public void deleteById(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            preOperativeWriteRepository.findById(uuid)
                .ifPresent(preOperativeWriteRepository::delete);
        } catch (IllegalArgumentException ignored) {
            // UUID inválido, no se realiza acción
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