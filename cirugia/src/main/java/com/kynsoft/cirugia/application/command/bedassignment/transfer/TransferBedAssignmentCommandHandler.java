package com.kynsoft.cirugia.application.command.bedassignment.transfer;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.domain.dto.BedAssignment;
import com.kynsoft.cirugia.domain.dto.RecoveryBed;
import com.kynsoft.cirugia.domain.service.IBedAssignmentService;
import com.kynsoft.cirugia.domain.service.IRecoveryBedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransferBedAssignmentCommandHandler implements ICommandHandler<TransferBedAssignmentCommand> {

    private final IBedAssignmentService bedAssignmentService;
    private final IRecoveryBedService recoveryBedService;

    @Override
    @Transactional
    public void handle(TransferBedAssignmentCommand command) {
        log.info("Manejando comando para crear asignación de cama para cirugía: {}", command.getBedId());
        AtomicReference<BedAssignment> assignmentValue = new AtomicReference<>();
        bedAssignmentService.findById(command.getId())
                .ifPresent(assignment -> {
                    assignmentValue.set(assignment);
                    // Actualizar el estado de la asignación anterior a "COMPLETED"
                    assignment.setStatus("COMPLETED");
                    assignment.setReleaseDate(LocalDateTime.now());
                    bedAssignmentService.update(assignment);
                });
        RecoveryBed recoveryBed = recoveryBedService.findById(assignmentValue.get().getBedId());
        if (recoveryBed != null) {
            // Actualizar el estado de la cama a "ASSIGNED"
            recoveryBed.setStatus("AVAILABLE");
            recoveryBedService.update(recoveryBed);
        } else {
            log.warn("Cama con ID {} no encontrada para actualizar su estado.", command.getBedId());
        }

        RecoveryBed recoveryBedNew = recoveryBedService.findById(command.getBedId());
        if (recoveryBed != null) {
            // Actualizar el estado de la cama a "ASSIGNED"
            recoveryBed.setStatus("OCCUPIED");
            recoveryBedService.update(recoveryBed);
        } else {
            log.warn("Cama con ID {} no encontrada para actualizar su estado.", command.getBedId());
        }


        BedAssignment assignment = assignmentValue.get();
        assignment.setId(UUID.randomUUID());
        assignment.setPatientId(assignment.getPatientId());
        assignment.setBusinessId(command.getCreatedBy());
        assignment.setBedId(command.getBedId());
        assignment.setRoomId(command.getRoomId());
        assignment.setStatus("ASSIGNED");
        assignment.setAssignmentDate(LocalDateTime.now());
        assignment.setSurgeryId(assignment.getSurgeryId());
        assignment.setSurgeryStage(assignment.getSurgeryStage());
        assignment.setAssignedBy(command.getCreatedBy());
        assignment.setBusinessId(assignment.getBusinessId());

        
        // Llamar al servicio para crear la asignación (que también gestiona asignaciones previas)
        BedAssignment created = bedAssignmentService.createAndReplaceAssignment(assignment);


        // Configurar el mensaje de respuesta con la información de la asignación creada
        TransferBedAssignmentMessage responseMessage = TransferBedAssignmentMessage.builder()
                .id(created.getId())
                .patientId(created.getPatientId())
                .surgeryId(created.getSurgeryId())
                .bedId(created.getBedId())
                .roomId(created.getRoomId())
                .assignmentDate(created.getAssignmentDate())
                .status(created.getStatus())
                .surgeryStage(created.getSurgeryStage())
                .observations(created.getObservations())
                .assignedBy(created.getAssignedBy())
                .businessId(created.getBusinessId())
                .build();
        
        command.setMessage(responseMessage);
        
        log.info("Asignación de cama creada exitosamente con ID: {}", created.getId());
    }
}
