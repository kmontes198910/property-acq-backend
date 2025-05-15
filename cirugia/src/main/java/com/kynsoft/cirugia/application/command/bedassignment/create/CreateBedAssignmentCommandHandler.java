package com.kynsoft.cirugia.application.command.bedassignment.create;

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

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateBedAssignmentCommandHandler implements ICommandHandler<CreateBedAssignmentCommand> {

    private final IBedAssignmentService bedAssignmentService;
    private final IRecoveryBedService recoveryBedService;

    @Override
    @Transactional
    public void handle(CreateBedAssignmentCommand command) {
        log.info("Manejando comando para crear asignación de cama para cirugía: {}", command.getSurgeryId());
        RecoveryBed recoveryBed = recoveryBedService.findById(command.getBedId());
        if (recoveryBed != null) {
            // Actualizar el estado de la cama a "ASSIGNED"
            recoveryBed.setStatus("ASSIGNED");
            recoveryBedService.update(recoveryBed);
        } else {
            log.warn("Cama con ID {} no encontrada para actualizar su estado.", command.getBedId());
        }

        // Crear DTO para la asignación de cama
        BedAssignment bedAssignment = BedAssignment.builder()
                .id(command.getId() != null ? command.getId() : UUID.randomUUID())
                .patientId(command.getPatientId())
                .surgeryId(command.getSurgeryId())
                .bedId(command.getBedId())
                .roomId(command.getRoomId())
                .assignmentDate(LocalDateTime.now())
                .status("ASSIGNED")
                .surgeryStage(command.getSurgeryStage())
                .observations(command.getObservations())
                .assignedBy(command.getAssignedBy())
                .businessId(command.getBusinessId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy(command.getCreatedBy())
                .updatedBy(command.getCreatedBy())
                .build();
        
        // Llamar al servicio para crear la asignación (que también gestiona asignaciones previas)
        BedAssignment created = bedAssignmentService.createAndReplaceAssignment(bedAssignment);


        // Configurar el mensaje de respuesta con la información de la asignación creada
        CreateBedAssignmentMessage responseMessage = CreateBedAssignmentMessage.builder()
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
