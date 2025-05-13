package com.kynsoft.cirugia.infrastructure.repository;

import com.kynsoft.cirugia.domain.dto.BedAssignment;
import com.kynsoft.cirugia.domain.service.IBedAssignmentRepository;
import com.kynsoft.cirugia.infrastructure.entities.BedAssignmentEntity;
import com.kynsoft.cirugia.infrastructure.repository.command.BedAssignmentWriteRepository;
import com.kynsoft.cirugia.infrastructure.repository.command.RecoveryBedWriteRepository;
import com.kynsoft.cirugia.infrastructure.repository.query.BedAssignmentReadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementación del repositorio para operaciones de asignación de camas
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class BedAssignmentRepositoryImpl implements IBedAssignmentRepository {

    private final BedAssignmentReadRepository bedAssignmentReadRepository;
    private final BedAssignmentWriteRepository bedAssignmentWriteRepository;
    private final RecoveryBedWriteRepository recoveryBedWriteRepository;
    
    private static final String STATUS_ASSIGNED = "ASSIGNED";
    private static final String STATUS_COMPLETED = "COMPLETED";
    private static final String BED_STATUS_AVAILABLE = "AVAILABLE";
    private static final String BED_STATUS_OCCUPIED = "OCCUPIED";

    /**
     * Crea una nueva asignación de cama y gestiona asignaciones previas
     * 
     * @param bedAssignment Datos de la asignación de cama
     * @return Asignación de cama creada
     */
    @Override
    @Transactional
    public BedAssignment createAssignment(BedAssignment bedAssignment) {
        log.info("Creando nueva asignación de cama para paciente {} en cirugía {}", 
                bedAssignment.getPatientId(), bedAssignment.getSurgeryId());
        
        // 1. Buscar asignaciones activas para la misma cirugía
        List<BedAssignmentEntity> activeAssignments = bedAssignmentReadRepository.findBySurgeryId(bedAssignment.getSurgeryId())
                .stream()
                .filter(a -> STATUS_ASSIGNED.equals(a.getStatus()))
                .collect(Collectors.toList());
        
        // 2. Si hay asignaciones activas, marcarlas como completadas
        if (!activeAssignments.isEmpty()) {
            log.info("Encontradas {} asignaciones activas previas para la cirugía", activeAssignments.size());
            for (BedAssignmentEntity prevAssignment : activeAssignments) {
                // Actualizar el estado de la asignación anterior
                bedAssignmentWriteRepository.updateStatus(prevAssignment.getId(), STATUS_COMPLETED);
                bedAssignmentWriteRepository.release(prevAssignment.getId(), LocalDateTime.now());
                
                // Marcar la cama anterior como disponible
                recoveryBedWriteRepository.updateStatus(prevAssignment.getBedId(), BED_STATUS_AVAILABLE);
                
                log.info("Liberada asignación previa con ID: {}, Cama ID: {}", 
                        prevAssignment.getId(), prevAssignment.getBedId());
            }
        }
        
        // 3. Crear la nueva asignación
        BedAssignmentEntity entity = mapToEntity(bedAssignment);
        
        // Establecer valores predeterminados si no se proporcionan
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        if (entity.getAssignmentDate() == null) {
            entity.setAssignmentDate(LocalDateTime.now());
        }
        if (entity.getStatus() == null) {
            entity.setStatus(STATUS_ASSIGNED);
        }
        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(LocalDateTime.now());
        }
        if (entity.getUpdatedAt() == null) {
            entity.setUpdatedAt(LocalDateTime.now());
        }
        
        // 4. Marcar la nueva cama como ocupada
        recoveryBedWriteRepository.updateStatus(entity.getBedId(), BED_STATUS_OCCUPIED);
        
        // 5. Guardar y devolver la nueva asignación
        BedAssignmentEntity savedEntity = bedAssignmentWriteRepository.save(entity);
        log.info("Creada nueva asignación con ID: {}, Cama ID: {}", savedEntity.getId(), savedEntity.getBedId());
        
        return mapToDomain(savedEntity);
    }

    /**
     * Busca todas las asignaciones de cama por ID de cirugía
     * 
     * @param surgeryId ID de la cirugía
     * @return Lista de asignaciones de cama para la cirugía especificada
     */
    @Override
    public List<BedAssignment> findBySurgeryId(UUID surgeryId) {
        log.info("Buscando asignaciones de cama para cirugía con ID: {}", surgeryId);
        return bedAssignmentReadRepository.findBySurgeryId(surgeryId)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }
    
    /**
     * Convierte una entidad de asignación de cama a objeto de dominio
     */
    private BedAssignment mapToDomain(BedAssignmentEntity entity) {
        return BedAssignment.builder()
                .id(entity.getId())
                .patientId(entity.getPatientId())
                .surgeryId(entity.getSurgeryId())
                .bedId(entity.getBedId())
                .roomId(entity.getRoomId())
                .assignmentDate(entity.getAssignmentDate())
                .releaseDate(entity.getReleaseDate())
                .status(entity.getStatus())
                .surgeryStage(entity.getSurgeryStage())
                .observations(entity.getObservations())
                .assignedBy(entity.getAssignedBy())
                .businessId(entity.getBusinessId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }
    
    /**
     * Convierte un objeto de dominio a entidad de asignación de cama
     */
    private BedAssignmentEntity mapToEntity(BedAssignment bedAssignment) {
        return BedAssignmentEntity.builder()
                .id(bedAssignment.getId())
                .patientId(bedAssignment.getPatientId())
                .surgeryId(bedAssignment.getSurgeryId())
                .bedId(bedAssignment.getBedId())
                .roomId(bedAssignment.getRoomId())
                .assignmentDate(bedAssignment.getAssignmentDate())
                .releaseDate(bedAssignment.getReleaseDate())
                .status(bedAssignment.getStatus())
                .surgeryStage(bedAssignment.getSurgeryStage())
                .observations(bedAssignment.getObservations())
                .assignedBy(bedAssignment.getAssignedBy())
                .businessId(bedAssignment.getBusinessId())
                .createdAt(bedAssignment.getCreatedAt())
                .updatedAt(bedAssignment.getUpdatedAt())
                .createdBy(bedAssignment.getCreatedBy())
                .updatedBy(bedAssignment.getUpdatedBy())
                .build();
    }
}
