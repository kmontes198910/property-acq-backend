package com.kynsoft.cirugia.infrastructure.services;

import com.kynsoft.cirugia.domain.dto.PreOperative;
import com.kynsoft.cirugia.domain.service.IPreOperativeRepository;
import com.kynsoft.cirugia.infrastructure.entities.PreOperativeEntity;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PreOperativeRepository implements IPreOperativeRepository {

    private final EntityManager entityManager;

    public PreOperativeRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public PreOperative save(PreOperative preOperative) {
        if (preOperative.getId() == null) {
            preOperative.setId(UUID.randomUUID());
            preOperative.setCreatedAt(LocalDateTime.now());
        }
        preOperative.setUpdatedAt(LocalDateTime.now());
        
        PreOperativeEntity entity = mapToEntity(preOperative);
        entityManager.merge(entity);
        entityManager.flush();
        return mapToDto(entity);
    }

    @Override
    public Optional<PreOperative> findById(String id) {
        try {
            PreOperativeEntity entity = entityManager.find(PreOperativeEntity.class, UUID.fromString(id));
            return Optional.ofNullable(entity).map(this::mapToDto);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<PreOperative> findBySurgeryId(UUID surgeryId) {
        try {
            TypedQuery<PreOperativeEntity> query = entityManager.createQuery(
                    "SELECT p FROM PreOperativeEntity p WHERE p.surgeryId = :surgeryId", 
                    PreOperativeEntity.class);
            query.setParameter("surgeryId", surgeryId);
            PreOperativeEntity entity = query.getSingleResult();
            return Optional.ofNullable(entity).map(this::mapToDto);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(String id) {
        PreOperativeEntity entity = entityManager.find(PreOperativeEntity.class, UUID.fromString(id));
        if (entity != null) {
            entityManager.remove(entity);
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