package com.kynsoft.cirugia.infrastructure.services;

import com.kynsof.share.core.domain.exception.BusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.cirugia.domain.service.IBedAssignmentService;
import com.kynsoft.cirugia.domain.dto.BedAssignment;
import com.kynsoft.cirugia.infrastructure.config.SurgeryCacheConfig;
import com.kynsoft.cirugia.infrastructure.entities.BedAssignmentEntity;
import com.kynsoft.cirugia.infrastructure.entities.RecoveryBedEntity;
import com.kynsoft.cirugia.infrastructure.repository.command.BedAssignmentWriteRepository;
import com.kynsoft.cirugia.infrastructure.repository.command.RecoveryBedWriteRepository;
import com.kynsoft.cirugia.infrastructure.repository.query.BedAssignmentReadRepository;
import com.kynsoft.cirugia.infrastructure.repository.query.RecoveryBedReadRepository;
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
public class BedAssignmentServiceImpl implements IBedAssignmentService {

    private final BedAssignmentReadRepository bedAssignmentReadRepository;
    private final BedAssignmentWriteRepository bedAssignmentWriteRepository;
    private final RecoveryBedReadRepository recoveryBedReadRepository;
    private final RecoveryBedWriteRepository recoveryBedWriteRepository;

    @Override
    @Cacheable(value = SurgeryCacheConfig.BED_ASSIGNMENT_CACHE, key = "#id")
    public Optional<BedAssignment> findById(UUID id) {
        log.info("Finding bed assignment with ID: {}", id);
        return bedAssignmentReadRepository.findById(id).map(this::mapToDomain);
    }

    @Override
    @Cacheable(value = SurgeryCacheConfig.BED_ASSIGNMENT_CACHE, key = "'patient_' + #patientId")
    public List<BedAssignment> findByPatientId(UUID patientId) {
        log.info("Finding bed assignments for patient ID: {}", patientId);
        return bedAssignmentReadRepository.findByPatientId(patientId)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = SurgeryCacheConfig.BED_ASSIGNMENT_CACHE, key = "'surgery_' + #surgeryId")
    public List<BedAssignment> findBySurgeryId(UUID surgeryId) {
        log.info("Finding bed assignments for surgery ID: {}", surgeryId);
        return bedAssignmentReadRepository.findBySurgeryId(surgeryId)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = SurgeryCacheConfig.BED_ASSIGNMENT_CACHE, key = "'bed_' + #bedId")
    public List<BedAssignment> findByBedId(UUID bedId) {
        log.info("Finding bed assignments for bed ID: {}", bedId);
        return bedAssignmentReadRepository.findByBedId(bedId)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = SurgeryCacheConfig.BED_ASSIGNMENT_CACHE, key = "'business_' + #businessId")
    public List<BedAssignment> findByBusinessId(UUID businessId) {
        log.info("Finding bed assignments for business ID: {}", businessId);
        return bedAssignmentReadRepository.findByBusinessId(businessId)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = SurgeryCacheConfig.BED_ASSIGNMENT_CACHE, key = "'status_' + #status")
    public List<BedAssignment> findByStatus(String status) {
        log.info("Finding bed assignments with status: {}", status);
        return bedAssignmentReadRepository.findByStatus(status)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = SurgeryCacheConfig.BED_ASSIGNMENT_CACHE, key = "'active_' + #businessId")
    public List<BedAssignment> findActiveAssignments(UUID businessId) {
        log.info("Finding active bed assignments for business ID: {}", businessId);
        return bedAssignmentReadRepository.findActiveAssignments(businessId)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CacheEvict(value = {SurgeryCacheConfig.BED_ASSIGNMENT_CACHE, SurgeryCacheConfig.RECOVERY_BED_CACHE}, allEntries = true)
    public BedAssignment assignBed(BedAssignment bedAssignment) {
        log.info("Asignando cama a paciente. Cama ID: {}, Paciente ID: {}", bedAssignment.getBedId(), bedAssignment.getPatientId());
        
        // Verificar que la cama existe y está disponible
        Optional<RecoveryBedEntity> optionalBed = recoveryBedReadRepository.findById(bedAssignment.getBedId());
        
        if (!optionalBed.isPresent()) {
            log.error("Cama con ID {} no encontrada", bedAssignment.getBedId());
           // throw new BusinessException("La cama especificada no existe");
        }
        
        RecoveryBedEntity bed = optionalBed.get();
//        if (!RecoveryBedConstants.BED_STATUS_AVAILABLE.equals(bed.getStatus()) &&
//            !RecoveryBedConstants.BED_STATUS_RESERVED.equals(bed.getStatus())) {
//            log.error("Cama con ID {} no está disponible para asignación. Estado actual: {}", bed.getId(), bed.getStatus());
//            throw new BusinessException("La cama no está disponible para asignación");
//        }
//
        // Verificar que la cirugía existe si se proporciona un ID de cirugía
        if (bedAssignment.getSurgeryId() != null) {
            // La verificación de existencia de cirugía debería hacerse aquí
            log.debug("Verificando existencia de cirugía con ID: {}", bedAssignment.getSurgeryId());
        }
        
        // Establecer los valores iniciales de la asignación
        bedAssignment.setId(UUID.randomUUID());
        bedAssignment.setAssignmentDate(LocalDateTime.now());
        bedAssignment.setCreatedAt(LocalDateTime.now());
        bedAssignment.setUpdatedAt(LocalDateTime.now());
        
        if (bedAssignment.getStatus() == null) {
          //  bedAssignment.setStatus(RecoveryBedConstants.ASSIGNMENT_STATUS_ASSIGNED);
        }
        
        // Cambiar el estado de la cama a ocupada
       // recoveryBedWriteRepository.updateStatus(bed.getId(), RecoveryBedConstants.BED_STATUS_OCCUPIED);
        
        // Guardar la asignación
        BedAssignmentEntity entity = mapToEntity(bedAssignment);
        BedAssignmentEntity savedEntity = bedAssignmentWriteRepository.save(entity);
        
        log.info("Cama asignada correctamente. ID de asignación: {}", savedEntity.getId());
        return mapToDomain(savedEntity);
    }

    @Override
    @Transactional
    @CacheEvict(value = SurgeryCacheConfig.BED_ASSIGNMENT_CACHE, allEntries = true)
    public BedAssignment update(BedAssignment bedAssignment) {
        log.info("Updating bed assignment with ID: {}", bedAssignment.getId());
        
        bedAssignment.setUpdatedAt(LocalDateTime.now());
        BedAssignmentEntity entity = mapToEntity(bedAssignment);
        BedAssignmentEntity savedEntity = bedAssignmentWriteRepository.save(entity);
        
        return mapToDomain(savedEntity);
    }

    @Override
    @Transactional
    @CacheEvict(value = SurgeryCacheConfig.BED_ASSIGNMENT_CACHE, allEntries = true)
    public void updateStatus(UUID assignmentId, String status) {
        log.info("Updating status of bed assignment with ID {} to {}", assignmentId, status);
        bedAssignmentWriteRepository.updateStatus(assignmentId, status);
    }

    @Override
    @Transactional
    @CacheEvict(value = {SurgeryCacheConfig.BED_ASSIGNMENT_CACHE, SurgeryCacheConfig.RECOVERY_BED_CACHE}, allEntries = true)
    public void releaseBed(UUID assignmentId, UUID releasedBy) {
        log.info("Releasing bed for assignment with ID: {}", assignmentId);
        
        Optional<BedAssignmentEntity> optionalAssignment = bedAssignmentReadRepository.findById(assignmentId);
        
        if (!optionalAssignment.isPresent()) {
            log.error("Bed assignment with ID {} not found", assignmentId);
          //  throw new BusinessException("La asignación especificada no existe");
        }
        
        BedAssignmentEntity assignment = optionalAssignment.get();
        LocalDateTime releaseDate = LocalDateTime.now();
        
        // Actualizar la asignación
        bedAssignmentWriteRepository.release(assignmentId, releaseDate, releasedBy);
        
        // Cambiar el estado de la cama a disponible
      //  recoveryBedWriteRepository.updateStatus(assignment.getBedId(), RecoveryBedConstants.BED_STATUS_AVAILABLE);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        log.info("Searching bed assignments with filter criteria: {}", filterCriteria);
        GenericSpecificationsBuilder<BedAssignmentEntity> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<BedAssignmentEntity> data = bedAssignmentReadRepository.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<BedAssignmentEntity> data) {
        List<BedAssignment> bedAssignments = new ArrayList<>();
        for (BedAssignmentEntity entity : data.getContent()) {
            bedAssignments.add(mapToDomain(entity));
        }
        return new PaginatedResponse(
            bedAssignments, 
            data.getTotalPages(), 
            data.getNumberOfElements(),
            data.getTotalElements(), 
            data.getSize(), 
            data.getNumber()
        );
    }

    private BedAssignment mapToDomain(BedAssignmentEntity entity) {
        return BedAssignment.builder()
                .id(entity.getId())
                .patientId(entity.getPatientId())
                .surgeryId(entity.getSurgeryId())
                .bedId(entity.getBedId())
                .assignmentDate(entity.getAssignmentDate())
                .plannedReleaseDate(entity.getPlannedReleaseDate())
                .actualReleaseDate(entity.getActualReleaseDate())
                .status(entity.getStatus())
                .medicalNotes(entity.getMedicalNotes())
                .vitalSigns(entity.getVitalSigns())
                .careInstructions(entity.getCareInstructions())
                .assignedBy(entity.getAssignedBy())
                .releasedBy(entity.getReleasedBy())
                .businessId(entity.getBusinessId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }

    private BedAssignmentEntity mapToEntity(BedAssignment bedAssignment) {
        return BedAssignmentEntity.builder()
                .id(bedAssignment.getId())
                .patientId(bedAssignment.getPatientId())
                .surgeryId(bedAssignment.getSurgeryId())
                .bedId(bedAssignment.getBedId())
                .assignmentDate(bedAssignment.getAssignmentDate())
                .plannedReleaseDate(bedAssignment.getPlannedReleaseDate())
                .actualReleaseDate(bedAssignment.getActualReleaseDate())
                .status(bedAssignment.getStatus())
                .medicalNotes(bedAssignment.getMedicalNotes())
                .vitalSigns(bedAssignment.getVitalSigns())
                .careInstructions(bedAssignment.getCareInstructions())
                .assignedBy(bedAssignment.getAssignedBy())
                .releasedBy(bedAssignment.getReleasedBy())
                .businessId(bedAssignment.getBusinessId())
                .createdAt(bedAssignment.getCreatedAt())
                .updatedAt(bedAssignment.getUpdatedAt())
                .createdBy(bedAssignment.getCreatedBy())
                .updatedBy(bedAssignment.getUpdatedBy())
                .build();
    }
}