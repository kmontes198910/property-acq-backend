package com.kynsoft.cirugia.infrastructure.service;

import com.kynsof.share.core.domain.exception.BusinessException;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.cirugia.application.query.medicalteam.MedicalTeamSearchResponse;
import com.kynsoft.cirugia.domain.dto.MedicalTeam;
import com.kynsoft.cirugia.domain.service.IMedicalTeamService;
import com.kynsoft.cirugia.infrastructure.config.SurgeryCacheConfig;
import com.kynsoft.cirugia.infrastructure.entities.MedicalTeamEntity;
import com.kynsoft.cirugia.infrastructure.repository.command.MedicalTeamWriteRepository;
import com.kynsoft.cirugia.infrastructure.repository.query.MedicalTeamReadRepository;
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
public class MedicalTeamServiceImpl implements IMedicalTeamService {

    private final MedicalTeamWriteRepository medicalTeamWriteRepository;
    private final MedicalTeamReadRepository medicalTeamReadRepository;

    @Override
    @Transactional
    @CacheEvict(cacheNames = SurgeryCacheConfig.SURGERY_SERVICE_CACHE, allEntries = true)
    public UUID createMedicalTeam(MedicalTeam medicalTeam) {
        log.info("Creating medical team member with ID: {}", medicalTeam.getId());

        if (medicalTeam.getId() == null) {
            medicalTeam.setId(UUID.randomUUID());
        }
        
        medicalTeam.setCreatedAt(LocalDateTime.now());
        
        MedicalTeamEntity entity = mapToEntity(medicalTeam);
        MedicalTeamEntity savedEntity = medicalTeamWriteRepository.save(entity);
        return savedEntity.getId();
    }
    
    @Override
    @Transactional
    @CacheEvict(cacheNames = SurgeryCacheConfig.SURGERY_SERVICE_CACHE, allEntries = true)
    public void deleteMedicalTeam(UUID id) {
        log.info("Deleting medical team member with ID: {}", id);
        
        if (!medicalTeamReadRepository.existsById(id)) {
            log.warn("Medical team member not found with ID: {}", id);
            throw new BusinessNotFoundException(
                new GlobalBusinessException(DomainErrorMessage.BUSINESS_NOT_FOUND, 
                new ErrorField("id", "Medical team member not found with ID: " + id)));
        }
        
        try {
            medicalTeamWriteRepository.deleteById(id);
        } catch (Exception e) {
            throw new BusinessException(DomainErrorMessage.NOT_DELETE, 
                "Medical team member cannot be deleted as it has related elements: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = SurgeryCacheConfig.SURGERY_SERVICE_CACHE, key = "#id", unless = "#result == null")
    public Optional<MedicalTeam> getMedicalTeamById(UUID id) {
        log.info("Finding medical team member with ID: {}", id);
        return medicalTeamReadRepository.findById(id)
                .map(this::mapToDomain);
    }
    
    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = SurgeryCacheConfig.SURGERY_SERVICE_CACHE, 
              key = "'medicalTeams:surgeryId:' + #surgeryId", 
              unless = "#result == null")
    public List<MedicalTeam> listMedicalTeamsBySurgery(UUID surgeryId) {
        log.info("Listing medical team members for surgery: {}", surgeryId);
        return medicalTeamReadRepository.findBySurgeryId(surgeryId)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = SurgeryCacheConfig.SURGERY_SERVICE_CACHE, 
              key = "'medicalTeams:memberId:' + #memberId", 
              unless = "#result == null")
    public List<MedicalTeam> listMedicalTeamsByMember(UUID memberId) {
        log.info("Listing medical team entries for member: {}", memberId);
        return medicalTeamReadRepository.findByMemberId(memberId)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = SurgeryCacheConfig.SURGERY_SERVICE_CACHE, 
              key = "'medicalTeams:businessId:' + #businessId", 
              unless = "#result == null")
    public List<MedicalTeam> listMedicalTeamsByBusiness(UUID businessId) {
        log.info("Listing medical team members for business: {}", businessId);
        return medicalTeamReadRepository.findByBusinessId(businessId)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = SurgeryCacheConfig.SURGERY_SERVICE_CACHE, 
              key = "'medicalTeams:role:' + #role", 
              unless = "#result == null")
    public List<MedicalTeam> listMedicalTeamsByRole(String role) {
        log.info("Listing medical team members with role: {}", role);
        return medicalTeamReadRepository.findByRole(role)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = SurgeryCacheConfig.SURGERY_SERVICE_CACHE,
            key = "'medicalTeams:search:' + #pageable.pageNumber + ':' + #pageable.pageSize + ':' + #pageable.sort + ':' + T(java.util.Objects).hash(#filterCriteria)",
            unless = "#result == null")
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        log.info("Searching medical team members with filters: {}", filterCriteria);
        
        GenericSpecificationsBuilder<MedicalTeamEntity> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<MedicalTeamEntity> data = medicalTeamReadRepository.findAll(specifications, pageable);
        
        return getPaginatedResponse(data);
    }
    
    private PaginatedResponse getPaginatedResponse(Page<MedicalTeamEntity> data) {
        List<MedicalTeamSearchResponse> items = data.getContent().stream()
                .map(entity -> MedicalTeamSearchResponse.builder()
                        .id(entity.getId())
                        .surgeryId(entity.getSurgeryId())
                        .memberId(entity.getMemberId())
                        .memberName(entity.getMemberName())
                        .memberLastName(entity.getMemberLastName())
                        .specialtyName(entity.getSpecialtyName())
                        .specialtyCode(entity.getSpecialtyCode())
                        .role(entity.getRole())
                        .businessId(entity.getBusinessId())
                        .createdAt(entity.getCreatedAt())
                        .updatedAt(entity.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());
                

                
        return new PaginatedResponse(
                items,
            data.getTotalPages(), 
            data.getNumberOfElements(),
            data.getTotalElements(), 
            data.getSize(), 
            data.getNumber()
        );
    }
    
    private MedicalTeam mapToDomain(MedicalTeamEntity entity) {
        return MedicalTeam.builder()
                .id(entity.getId())
                .surgeryId(entity.getSurgeryId())
                .memberId(entity.getMemberId())
                .memberName(entity.getMemberName())
                .memberLastName(entity.getMemberLastName())
                .specialtyName(entity.getSpecialtyName())
                .specialtyCode(entity.getSpecialtyCode())
                .role(entity.getRole())
                .businessId(entity.getBusinessId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }
    
    private MedicalTeamEntity mapToEntity(MedicalTeam medicalTeam) {
        return MedicalTeamEntity.builder()
                .id(medicalTeam.getId())
                .surgeryId(medicalTeam.getSurgeryId())
                .memberId(medicalTeam.getMemberId())
                .memberName(medicalTeam.getMemberName())
                .memberLastName(medicalTeam.getMemberLastName())
                .specialtyName(medicalTeam.getSpecialtyName())
                .specialtyCode(medicalTeam.getSpecialtyCode())
                .role(medicalTeam.getRole())
                .businessId(medicalTeam.getBusinessId())
                .createdAt(medicalTeam.getCreatedAt())
                .updatedAt(medicalTeam.getUpdatedAt())
                .createdBy(medicalTeam.getCreatedBy())
                .updatedBy(medicalTeam.getUpdatedBy())
                .build();
    }
}