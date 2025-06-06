package com.kynsoft.cirugia.infrastructure.services;

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
import java.util.List;
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
    @CacheEvict(cacheNames = SurgeryCacheConfig.TEAM_MEDICAL_CACHE, allEntries = true)
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
    @CacheEvict(cacheNames = SurgeryCacheConfig.TEAM_MEDICAL_CACHE, allEntries = true)
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
    @Cacheable(cacheNames = SurgeryCacheConfig.TEAM_MEDICAL_CACHE,
            key = "'medicalTeams:search:' + #pageable.pageNumber + ':' + #pageable.pageSize + ':' + #pageable.sort + ':' + T(java.util.Objects).hash(#filterCriteria)",
            unless = "#result == null")
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        log.info("Searching medical team members with filters: {}", filterCriteria);
        
        GenericSpecificationsBuilder<MedicalTeamEntity> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<MedicalTeamEntity> data = medicalTeamReadRepository.findAll(specifications, pageable);
        
        return getPaginatedResponse(data);
    }
    
    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = SurgeryCacheConfig.TEAM_MEDICAL_CACHE,
            key = "'medicalTeamsBySurgery:' + #surgeryId",
            unless = "#result == null || #result.isEmpty()")
    public List<MedicalTeam> findBySurgeryId(UUID surgeryId) {
        log.info("Fetching all medical team members for surgery ID: {}", surgeryId);
        
        List<MedicalTeamEntity> medicalTeamEntities = medicalTeamReadRepository.findBySurgeryId(surgeryId);
        return medicalTeamEntities.stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = SurgeryCacheConfig.TEAM_MEDICAL_CACHE,
            key = "'isMemberInSurgeryTeam:' + #surgeryId + ':' + #memberId",
            unless = "#result == false")
    public boolean isMemberInSurgeryTeam(UUID surgeryId, UUID memberId) {
        log.info("Checking if member ID: {} is part of the medical team for surgery ID: {}", memberId, surgeryId);
        
        List<MedicalTeamEntity> surgeryTeam = medicalTeamReadRepository.findBySurgeryId(surgeryId);
        
        if (surgeryTeam.isEmpty()) {
            log.info("No medical team found for surgery ID: {}", surgeryId);
            return false;
        }
        
        boolean isMemberInTeam = surgeryTeam.stream()
                .anyMatch(member -> member.getMemberId().equals(memberId));
        
        log.info("Member ID: {} is{} part of the medical team for surgery ID: {}", 
                memberId, isMemberInTeam ? "" : " not", surgeryId);
        
        return isMemberInTeam;
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
                        .specialityType(entity.getSpecialityType())
                        .role(entity.getRole())
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
                .specialityType(entity.getSpecialityType())
                .role(entity.getRole())
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
                .specialityType(medicalTeam.getSpecialityType())
                .role(medicalTeam.getRole())
                .createdAt(medicalTeam.getCreatedAt())
                .updatedAt(medicalTeam.getUpdatedAt())
                .createdBy(medicalTeam.getCreatedBy())
                .updatedBy(medicalTeam.getUpdatedBy())
                .build();
    }
}