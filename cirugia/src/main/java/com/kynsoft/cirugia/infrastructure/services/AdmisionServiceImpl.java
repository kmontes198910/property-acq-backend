package com.kynsoft.cirugia.infrastructure.services;

import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsoft.cirugia.domain.dto.AdmisionDto;
import com.kynsoft.cirugia.domain.service.IAdmisionService;
import com.kynsoft.cirugia.infrastructure.entities.AdmisionEntity;
import com.kynsoft.cirugia.infrastructure.mapper.AdmisionMapper;
import com.kynsoft.cirugia.infrastructure.repository.command.AdmisionWriteRepository;
import com.kynsoft.cirugia.infrastructure.repository.query.AdmisionReadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of admission management service
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AdmisionServiceImpl implements IAdmisionService {

    private final AdmisionWriteRepository admisionWriteRepository;
    private final AdmisionReadRepository admisionReadRepository;
    private final AdmisionMapper admisionMapper;

    @Override
    @Transactional
    public UUID createAdmision(AdmisionDto admision) {
        log.info("Creating new admission");
        
        // Generate ID if not provided
        if (admision.getId() == null) {
            admision.setId(UUID.randomUUID());
        }
        
        // Convert DTO to entity
        AdmisionEntity entity = admisionMapper.toEntity(admision);
        
        // Save entity
        AdmisionEntity savedEntity = admisionWriteRepository.save(entity);
        log.info("Admission created with ID: {}", savedEntity.getId());
        
        return savedEntity.getId();
    }

    @Override
    @Transactional
    public void updateAdmision(AdmisionDto admision) {
        log.info("Updating admission with ID: {}", admision.getId());
        
        // Check if ID is provided
        if (admision.getId() == null) {
            throw new IllegalArgumentException("The admission ID cannot be null for updates");
        }
        
        // Verify admission exists in database
        AdmisionEntity existingEntity = admisionWriteRepository.findById(admision.getId())
                .orElseThrow(() -> new BusinessNotFoundException(
                    new GlobalBusinessException(DomainErrorMessage.BUSINESS_NOT_FOUND, 
                    new ErrorField("id", "Admission not found with ID: " + admision.getId()))));
        
        // Update data
        existingEntity.setRoomId(admision.getRoom());
        existingEntity.setBedId(admision.getBed());
        existingEntity.setObservations(admision.getObservations());
        existingEntity.setUpdatedAt(LocalDateTime.now());
        existingEntity.setUpdatedBy(admision.getUpdatedBy());
        
        // Save changes
        admisionWriteRepository.save(existingEntity);
        log.info("Admission successfully updated, ID: {}", existingEntity.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AdmisionDto> getAdmisionBySurgeryId(UUID surgeryId) {
        log.info("Finding admission for surgery with ID: {}", surgeryId);
        
        // Directly search for admission by surgery ID
        return admisionReadRepository.findBySurgeryId(surgeryId)
                .map(admisionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AdmisionDto> getAdmisionById(UUID id) {
        log.info("Finding admission with ID: {}", id);
        
        return admisionReadRepository.findById(id)
                .map(admisionMapper::toDto);
    }
}
