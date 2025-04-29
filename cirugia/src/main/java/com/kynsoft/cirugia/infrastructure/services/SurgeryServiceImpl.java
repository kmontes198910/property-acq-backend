package com.kynsoft.cirugia.infrastructure.services;

import com.kynsof.share.core.domain.exception.BusinessException;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.cirugia.application.query.SurgeryResponse;
import com.kynsoft.cirugia.domain.interfaces.ISurgeryService;
import com.kynsoft.cirugia.domain.model.Surgery;
import com.kynsoft.cirugia.infrastructure.config.SurgeryCacheConfig;
import com.kynsoft.cirugia.infrastructure.entities.SurgeryEntity;
import com.kynsoft.cirugia.infrastructure.repository.command.SurgeryWriteRepository;
import com.kynsoft.cirugia.infrastructure.repository.query.SurgeryReadRepository;
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
public class SurgeryServiceImpl implements ISurgeryService {

    private final SurgeryWriteRepository surgeryWriteRepository;
    private final SurgeryReadRepository surgeryReadRepository;

    @Override
    @Transactional
    @CacheEvict(cacheNames = SurgeryCacheConfig.SURGERY_SERVICE_CACHE, allEntries = true)
    public UUID createSurgery(Surgery surgery) {
        log.info("Creating surgery for patient: {}", surgery.getPatientId());
        
        SurgeryEntity entity = mapToEntity(surgery);
        entity.setStatus("SCHEDULED"); // Default status for a new surgery
        entity = surgeryWriteRepository.save(entity);
        
        return entity.getId();
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = SurgeryCacheConfig.SURGERY_SERVICE_CACHE, allEntries = true)
    public void updateSurgery(Surgery surgery) {
        log.info("Updating surgery with ID: {}", surgery.getId());
        
        if (surgery.getId() == null) {
            throw new BusinessException(DomainErrorMessage.BUSINESS_NOT_FOUND, "Surgery ID cannot be null");
        }
        
        SurgeryEntity entity = surgeryReadRepository.findById(surgery.getId())
                .orElseThrow(() -> new BusinessNotFoundException(
                    new GlobalBusinessException(DomainErrorMessage.BUSINESS_NOT_FOUND, 
                    new ErrorField("id", "Surgery not found with ID: " + surgery.getId()))));

        // Update fields from surgery
        entity.setPatientId(surgery.getPatientId());
        entity.setDoctorId(surgery.getDoctorId());
        entity.setSpecialtyId(surgery.getSpecialtyId());
        entity.setSurgeryType(surgery.getSurgeryType());
        entity.setDescription(surgery.getDescription());
        entity.setScheduledDate(surgery.getScheduledDate());
        entity.setEstimatedDurationMinutes(surgery.getEstimatedDurationMinutes());
        entity.setUpdatedAt(LocalDateTime.now());
        
        surgeryWriteRepository.save(entity);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = SurgeryCacheConfig.SURGERY_SERVICE_CACHE, allEntries = true)
    public void changeSurgeryStatus(UUID surgeryId, String status, UUID updatedBy) {
        log.info("Changing surgery {} status to: {}", surgeryId, status);
        
        SurgeryEntity entity = surgeryReadRepository.findById(surgeryId)
                .orElseThrow(() -> new BusinessNotFoundException(
                    new GlobalBusinessException(DomainErrorMessage.BUSINESS_NOT_FOUND, 
                    new ErrorField("id", "Surgery not found with ID: " + surgeryId))));
        
        entity.setStatus(status);
        entity.setUpdatedAt(LocalDateTime.now());
        
        // Si el estado es COMPLETED, establecer la fecha de realización
        if (status.equals("COMPLETED")) {
            entity.setPerformedDate(LocalDateTime.now());
        }
        
        surgeryWriteRepository.save(entity);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = SurgeryCacheConfig.SURGERY_SERVICE_CACHE, allEntries = true)
    public void deleteSurgery(UUID id) {
        log.info("Deleting surgery with ID: {}", id);
        
        if (!surgeryReadRepository.existsById(id)) {
            log.warn("Surgery not found with ID: {}", id);
            throw new BusinessNotFoundException(
                new GlobalBusinessException(DomainErrorMessage.BUSINESS_NOT_FOUND, 
                new ErrorField("id", "Surgery not found with ID: " + id)));
        }
        
        try {
            surgeryWriteRepository.deleteById(id);
        } catch (Exception e) {
            throw new BusinessException(DomainErrorMessage.NOT_DELETE, 
                "Surgery cannot be deleted as it has related elements: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = SurgeryCacheConfig.SURGERY_SERVICE_CACHE, key = "#id", unless = "#result == null")
    public Optional<Surgery> getSurgeryById(UUID id) {
        log.info("Finding surgery with ID: {}", id);
        return surgeryReadRepository.findById(id)
                .map(this::mapToDomain);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = SurgeryCacheConfig.SURGERY_SERVICE_CACHE, 
              key = "'businessId:' + #businessId", 
              unless = "#result == null")
    public List<Surgery> listSurgeriesByBusiness(UUID businessId) {
        log.info("Listing surgeries for business: {}", businessId);
        return surgeryReadRepository.findByBusinessId(businessId)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = SurgeryCacheConfig.SURGERY_SERVICE_CACHE, 
              key = "'patientId:' + #patientId", 
              unless = "#result == null")
    public List<Surgery> listSurgeriesByPatient(UUID patientId) {
        log.info("Listing surgeries for patient: {}", patientId);
        return surgeryReadRepository.findByPatientId(patientId)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = SurgeryCacheConfig.SURGERY_SERVICE_CACHE, 
              key = "'dateRange:' + #startDate + ':' + #endDate + ':' + #businessId", 
              unless = "#result == null")
    public List<Surgery> listSurgeriesByDateRange(LocalDateTime startDate, LocalDateTime endDate, UUID businessId) {
        log.info("Listing surgeries between {} and {} for business: {}", startDate, endDate, businessId);
        return surgeryReadRepository.findByScheduledDateBetweenAndBusinessId(startDate, endDate, businessId)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = SurgeryCacheConfig.SURGERY_SERVICE_CACHE,
            key = "'search:' + #pageable.pageNumber + ':' + #pageable.pageSize + ':' + #pageable.sort + ':' + T(java.util.Objects).hash(#filterCriteria)",
            unless = "#result == null")
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        log.info("Searching surgeries with filters: {}", filterCriteria);
        
        GenericSpecificationsBuilder<SurgeryEntity> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<SurgeryEntity> data = surgeryReadRepository.findAll(specifications, pageable);
        
        return getPaginatedResponse(data);
    }
    
    private PaginatedResponse getPaginatedResponse(Page<SurgeryEntity> data) {
        List<SurgeryResponse> surgeryResponses = new ArrayList<>();
        for (SurgeryEntity entity : data.getContent()) {
            surgeryResponses.add(new SurgeryResponse(mapToDomain(entity)));
        }
        return new PaginatedResponse(
            surgeryResponses, 
            data.getTotalPages(), 
            data.getNumberOfElements(),
            data.getTotalElements(), 
            data.getSize(), 
            data.getNumber()
        );
    }
    
    private Surgery mapToDomain(SurgeryEntity entity) {
        return Surgery.builder()
                .id(entity.getId())
                .patientId(entity.getPatientId())
                .doctorId(entity.getDoctorId())
                .specialtyId(entity.getSpecialtyId())
                .surgeryType(entity.getSurgeryType())
                .description(entity.getDescription())
                .scheduledDate(entity.getScheduledDate())
                .performedDate(entity.getPerformedDate())
                .estimatedDurationMinutes(entity.getEstimatedDurationMinutes())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .businessId(entity.getBusinessId())
                .build();
    }
    
    private SurgeryEntity mapToEntity(Surgery surgery) {
        return SurgeryEntity.builder()
                .id(surgery.getId())
                .patientId(surgery.getPatientId())
                .doctorId(surgery.getDoctorId())
                .specialtyId(surgery.getSpecialtyId())
                .surgeryType(surgery.getSurgeryType())
                .description(surgery.getDescription())
                .scheduledDate(surgery.getScheduledDate())
                .performedDate(surgery.getPerformedDate())
                .estimatedDurationMinutes(surgery.getEstimatedDurationMinutes())
                .status(surgery.getStatus())
                .businessId(surgery.getBusinessId())
                .build();
    }
}