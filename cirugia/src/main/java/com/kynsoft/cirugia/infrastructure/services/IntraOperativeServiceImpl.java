package com.kynsoft.cirugia.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.cirugia.application.query.recoverybed.getbyid.RecoveryBedResponse;
import com.kynsoft.cirugia.domain.dto.IntraOperative;
import com.kynsoft.cirugia.domain.dto.RecoveryBed;
import com.kynsoft.cirugia.domain.enums.RecoveryBedConstants;
import com.kynsoft.cirugia.domain.service.IIntraOperativeRepository;
import com.kynsoft.cirugia.domain.service.IRecoveryBedService;
import com.kynsoft.cirugia.infrastructure.config.SurgeryCacheConfig;
import com.kynsoft.cirugia.infrastructure.entities.RecoveryBedEntity;
import com.kynsoft.cirugia.infrastructure.repository.command.IntraOperativeWriteRepository;
import com.kynsoft.cirugia.infrastructure.repository.command.RecoveryBedWriteRepository;
import com.kynsoft.cirugia.infrastructure.repository.query.IntraOperativeReadRepository;
import com.kynsoft.cirugia.infrastructure.repository.query.RecoveryBedReadRepository;
import com.kynsoft.cirugia.infrastructure.entities.IntraOperativeEntity;
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
@Transactional(readOnly = true)
public class IntraOperativeServiceImpl implements IIntraOperativeRepository {

    private final IntraOperativeReadRepository intraOperativeReadRepository;
    private final IntraOperativeWriteRepository intraOperativeWriteRepository;

    @Override
    @Transactional
    public IntraOperative create(IntraOperative intraOperative) {
        log.info("Creating new intraoperative: {}", intraOperative);
        intraOperative.setId(UUID.randomUUID());
        intraOperative.setCreatedAt(LocalDateTime.now());
        intraOperative.setUpdatedAt(LocalDateTime.now());
        IntraOperativeEntity entity = mapToEntity(intraOperative);
        IntraOperativeEntity savedEntity = intraOperativeWriteRepository.save(entity);
        return mapToDomain(savedEntity);
    }

    @Override
    public Optional<IntraOperative> findById(String id) {
        log.info("Finding intraoperative with ID: {}", id);
        return intraOperativeReadRepository.findById(UUID.fromString(id)).map(this::mapToDomain);
    }

    @Override
    public List<IntraOperative> findBySurgeryId(UUID surgeryId) {
        log.info("Finding intraoperatives for surgery ID: {}", surgeryId);
        return intraOperativeReadRepository.findBySurgeryId(surgeryId)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void update(IntraOperative intraOperative) {
        log.info("Updating intraoperative with ID: {}", intraOperative.getId());
        intraOperative.setUpdatedAt(LocalDateTime.now());
        IntraOperativeEntity entity = mapToEntity(intraOperative);
        intraOperativeWriteRepository.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        log.info("Deleting intraoperative with ID: {}", id);
        intraOperativeWriteRepository.deleteById(UUID.fromString(id));
    }

    private IntraOperative mapToDomain(IntraOperativeEntity entity) {
        return IntraOperative.builder()
                .id(entity.getId())
                .surgeryId(entity.getSurgeryId())
                .date(entity.getDate())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .procedureType(entity.getProcedureType())
                .anesthesiaType(entity.getAnesthesiaType())
                .projectedProcedure(entity.getProjectedProcedure())
                .performedProcedure(entity.getPerformedProcedure())
                .dieresis(entity.getDieresis())
                .expositionExploration(entity.getExpositionExploration())
                .surgicalFindings(entity.getSurgicalFindings())
                .bloodLoss(entity.getBloodLoss())
                .approximateBlood(entity.getApproximateBlood())
                .prostheticMaterial(entity.getProstheticMaterial())
                .surgicalProcedure(entity.getSurgicalProcedure())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }

    private IntraOperativeEntity mapToEntity(IntraOperative dto) {
        return IntraOperativeEntity.builder()
                .id(dto.getId())
                .surgeryId(dto.getSurgeryId())
                .date(dto.getDate())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .procedureType(dto.getProcedureType())
                .anesthesiaType(dto.getAnesthesiaType())
                .projectedProcedure(dto.getProjectedProcedure())
                .performedProcedure(dto.getPerformedProcedure())
                .dieresis(dto.getDieresis())
                .expositionExploration(dto.getExpositionExploration())
                .surgicalFindings(dto.getSurgicalFindings())
                .bloodLoss(dto.getBloodLoss())
                .approximateBlood(dto.getApproximateBlood())
                .prostheticMaterial(dto.getProstheticMaterial())
                .surgicalProcedure(dto.getSurgicalProcedure())
                .description(dto.getDescription())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .createdBy(dto.getCreatedBy())
                .updatedBy(dto.getUpdatedBy())
                .build();
    }
}
