package com.kynsoft.cirugia.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.cirugia.domain.service.IDiagnosisService;
import com.kynsoft.cirugia.domain.dto.Diagnosis;
import com.kynsoft.cirugia.infrastructure.config.SurgeryCacheConfig;
import com.kynsoft.cirugia.infrastructure.repository.command.DiagnosisWriteRepository;
import com.kynsoft.cirugia.infrastructure.repository.query.DiagnosisReadRepository;
import com.kynsoft.cirugia.application.query.diagnosis.getbyid.DiagnosisResponse;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class DiagnosisServiceImpl implements IDiagnosisService {

    private final DiagnosisReadRepository diagnosisReadRepository;
    private final DiagnosisWriteRepository diagnosisWriteRepository;

    @Override
    @Cacheable(value = SurgeryCacheConfig.DIAGNOSIS_CACHE, key = "#id")
    public Optional<Diagnosis> findById(UUID id) {
        log.info("Finding diagnosis with ID: {}", id);
        return diagnosisReadRepository.findById(id).map(this::mapToDomain);
    }

    @Override
    @Cacheable(value = SurgeryCacheConfig.DIAGNOSIS_CACHE, key = "'surgery_' + #surgeryId")
    public List<Diagnosis> findBySurgeryId(UUID surgeryId) {
        log.info("Finding diagnoses for surgery ID: {}", surgeryId);
        return diagnosisReadRepository.findBySurgeryId(surgeryId)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CacheEvict(value = SurgeryCacheConfig.DIAGNOSIS_CACHE, allEntries = true)
    public Diagnosis create(Diagnosis diagnosis) {
        log.info("Creating new diagnosis: {}", diagnosis);
        
        diagnosis.setId(UUID.randomUUID());
        diagnosis.setCreatedAt(LocalDateTime.now());
        diagnosis.setUpdatedAt(LocalDateTime.now());
        
        com.kynsoft.cirugia.infrastructure.entities.Diagnosis entity = mapToEntity(diagnosis);
        com.kynsoft.cirugia.infrastructure.entities.Diagnosis savedEntity = diagnosisWriteRepository.save(entity);
        
        return mapToDomain(savedEntity);
    }

    @Override
    @Transactional
    @CacheEvict(value = SurgeryCacheConfig.DIAGNOSIS_CACHE, allEntries = true)
    public Diagnosis update(Diagnosis diagnosis) {
        log.info("Updating diagnosis with ID: {}", diagnosis.getId());
        
        diagnosis.setUpdatedAt(LocalDateTime.now());
        com.kynsoft.cirugia.infrastructure.entities.Diagnosis entity = mapToEntity(diagnosis);
        com.kynsoft.cirugia.infrastructure.entities.Diagnosis savedEntity = diagnosisWriteRepository.save(entity);
        
        return mapToDomain(savedEntity);
    }

    @Override
    @Transactional
    @CacheEvict(value = SurgeryCacheConfig.DIAGNOSIS_CACHE, allEntries = true)
    public void delete(UUID id) {
        log.info("Deleting diagnosis with ID: {}", id);
        diagnosisWriteRepository.deleteById(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        log.info("Searching diagnoses with filter criteria: {}", filterCriteria);
        GenericSpecificationsBuilder<com.kynsoft.cirugia.infrastructure.entities.Diagnosis> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<com.kynsoft.cirugia.infrastructure.entities.Diagnosis> data = diagnosisReadRepository.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<com.kynsoft.cirugia.infrastructure.entities.Diagnosis> data) {
        List<DiagnosisResponse> diagnosisResponses = new ArrayList<>();
        for (com.kynsoft.cirugia.infrastructure.entities.Diagnosis entity : data.getContent()) {
            // Convertir a objeto de dominio y luego a response
            Diagnosis diagnosis = mapToDomain(entity);
            diagnosisResponses.add(new DiagnosisResponse(diagnosis));
        }
        return new PaginatedResponse(
            diagnosisResponses, 
            data.getTotalPages(), 
            data.getNumberOfElements(),
            data.getTotalElements(), 
            data.getSize(), 
            data.getNumber()
        );
    }

    private Diagnosis mapToDomain(com.kynsoft.cirugia.infrastructure.entities.Diagnosis entity) {
        return Diagnosis.builder()
                .id(entity.getId())
                .icdCode(entity.getIcdCode())
                .description(entity.getDescription())
                .type(entity.getType())
                .surgeryId(entity.getSurgeryId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }

    private com.kynsoft.cirugia.infrastructure.entities.Diagnosis mapToEntity(Diagnosis diagnosis) {
        com.kynsoft.cirugia.infrastructure.entities.Diagnosis entity = new com.kynsoft.cirugia.infrastructure.entities.Diagnosis();
        entity.setId(diagnosis.getId());
        entity.setIcdCode(diagnosis.getIcdCode());
        entity.setDescription(diagnosis.getDescription());
        entity.setType(diagnosis.getType());
        entity.setSurgeryId(diagnosis.getSurgeryId());
        entity.setCreatedAt(diagnosis.getCreatedAt());
        entity.setUpdatedAt(diagnosis.getUpdatedAt());
        entity.setCreatedBy(diagnosis.getCreatedBy());
        entity.setUpdatedBy(diagnosis.getUpdatedBy());
        return entity;
    }
}