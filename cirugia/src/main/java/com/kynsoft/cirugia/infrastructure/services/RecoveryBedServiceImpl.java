package com.kynsoft.cirugia.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.cirugia.domain.service.IRecoveryBedService;
import com.kynsoft.cirugia.domain.dto.RecoveryBed;
import com.kynsoft.cirugia.domain.enums.RecoveryBedConstants;
import com.kynsoft.cirugia.infrastructure.config.SurgeryCacheConfig;
import com.kynsoft.cirugia.infrastructure.entities.RecoveryBedEntity;
import com.kynsoft.cirugia.infrastructure.repository.command.RecoveryBedWriteRepository;
import com.kynsoft.cirugia.infrastructure.repository.query.RecoveryBedReadRepository;
import com.kynsoft.cirugia.application.query.recoverybed.getbyid.RecoveryBedResponse;
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
public class RecoveryBedServiceImpl implements IRecoveryBedService {

    private final RecoveryBedReadRepository recoveryBedReadRepository;
    private final RecoveryBedWriteRepository recoveryBedWriteRepository;

    @Override
    @Cacheable(value = SurgeryCacheConfig.RECOVERY_BED_CACHE, key = "#id")
    public RecoveryBed findById(UUID id) {
        log.info("Buscando cama de recuperación con ID: {}", id);
        Optional<RecoveryBed> recoveryBed = recoveryBedReadRepository.findById(id).map(this::mapToDomain);
        if (recoveryBed.isEmpty()) {
            log.error("Cama de recuperación no encontrada con ID: {}", id);
            throw new RuntimeException("Recovery Bed not found");
        }
        return recoveryBed.get();
    }


    @Override
    @Cacheable(value = SurgeryCacheConfig.RECOVERY_BED_CACHE, key = "'available_' + #businessId")
    public List<RecoveryBed> findAvailableBeds(UUID businessId) {
        log.info("Finding available recovery beds for business ID: {}", businessId);
        return recoveryBedReadRepository.findAvailableBeds(businessId)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = SurgeryCacheConfig.RECOVERY_BED_CACHE, key = "'status_' + #status")
    public List<RecoveryBed> findByStatus(String status) {
        log.info("Finding recovery beds with status: {}", status);
        return recoveryBedReadRepository.findByStatus(status)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = SurgeryCacheConfig.RECOVERY_BED_CACHE, key = "'type_' + #type")
    public List<RecoveryBed> findByType(String type) {
        log.info("Finding recovery beds of type: {}", type);
        return recoveryBedReadRepository.findByType(type)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CacheEvict(value = SurgeryCacheConfig.RECOVERY_BED_CACHE, allEntries = true)
    public RecoveryBed create(RecoveryBed recoveryBed) {
        log.info("Creating new recovery bed: {}", recoveryBed);

        if(recoveryBed.getId() == null) {

            recoveryBed.setId(UUID.randomUUID());
        }
        recoveryBed.setCreatedAt(LocalDateTime.now());
        recoveryBed.setUpdatedAt(LocalDateTime.now());
        
        if (recoveryBed.getStatus() == null) {
            recoveryBed.setStatus(RecoveryBedConstants.BED_STATUS_AVAILABLE);
        }
        
        RecoveryBedEntity entity = mapToEntity(recoveryBed);
        RecoveryBedEntity savedEntity = recoveryBedWriteRepository.save(entity);
        
        return mapToDomain(savedEntity);
    }

    @Override
    @Transactional
    @CacheEvict(value = SurgeryCacheConfig.RECOVERY_BED_CACHE, allEntries = true)
    public RecoveryBed update(RecoveryBed recoveryBed) {
        log.info("Updating recovery bed with ID: {}", recoveryBed.getId());
        
        recoveryBed.setUpdatedAt(LocalDateTime.now());
        RecoveryBedEntity entity = mapToEntity(recoveryBed);
        RecoveryBedEntity savedEntity = recoveryBedWriteRepository.save(entity);
        
        return mapToDomain(savedEntity);
    }

    @Override
    @Transactional
    @CacheEvict(value = SurgeryCacheConfig.RECOVERY_BED_CACHE, allEntries = true)
    public void updateStatus(UUID bedId, String status) {
        log.info("Updating status of recovery bed with ID {} to {}", bedId, status);
        recoveryBedWriteRepository.updateStatus(bedId, status);
    }

    @Override
    @Transactional
    @CacheEvict(value = SurgeryCacheConfig.RECOVERY_BED_CACHE, allEntries = true)
    public void delete(UUID id) {
        log.info("Deleting recovery bed with ID: {}", id);
        recoveryBedWriteRepository.deleteById(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        log.info("Searching recovery beds with filter criteria: {}", filterCriteria);
        GenericSpecificationsBuilder<RecoveryBedEntity> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<RecoveryBedEntity> data = recoveryBedReadRepository.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<RecoveryBedEntity> data) {
        List<RecoveryBedResponse> recoveryBedResponses = new ArrayList<>();
        for (RecoveryBedEntity entity : data.getContent()) {
            // Convertir a objeto de dominio y luego a response
            RecoveryBed recoveryBed = mapToDomain(entity);
            recoveryBedResponses.add(new RecoveryBedResponse(recoveryBed));
        }
        return new PaginatedResponse(
            recoveryBedResponses, 
            data.getTotalPages(), 
            data.getNumberOfElements(),
            data.getTotalElements(), 
            data.getSize(), 
            data.getNumber()
        );
    }

    private RecoveryBed mapToDomain(RecoveryBedEntity entity) {
        return RecoveryBed.builder()
                .id(entity.getId())
                .bedNumber(entity.getBedNumber())
                .location(entity.getLocation())
                .type(entity.getType())
                .status(entity.getStatus())
                .businessId(entity.getBusinessId())
                .floor(entity.getFloor())
                .recoveryRoomId(entity.getRecoveryRoomId())
                .lastMaintenanceDate(entity.getLastMaintenanceDate())
                .hasMonitor(entity.getHasMonitor())
                .hasOxygenSupply(entity.getHasOxygenSupply())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }

    private RecoveryBedEntity mapToEntity(RecoveryBed recoveryBed) {
        return RecoveryBedEntity.builder()
                .id(recoveryBed.getId())
                .bedNumber(recoveryBed.getBedNumber())
                .location(recoveryBed.getLocation())
                .type(recoveryBed.getType())
                .status(recoveryBed.getStatus())
                .businessId(recoveryBed.getBusinessId())
                .recoveryRoomId(recoveryBed.getRecoveryRoomId())
                .floor(recoveryBed.getFloor())
                .hasMonitor(recoveryBed.getHasMonitor())
                .hasOxygenSupply(recoveryBed.getHasOxygenSupply())
                .lastMaintenanceDate(recoveryBed.getLastMaintenanceDate())
                .createdAt(recoveryBed.getCreatedAt())
                .updatedAt(recoveryBed.getUpdatedAt())
                .createdBy(recoveryBed.getCreatedBy())
                .updatedBy(recoveryBed.getUpdatedBy())
                .build();
    }
}
