package com.kynsoft.cirugia.application.command.surgery.changestatus;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.domain.dto.BedAssignment;
import com.kynsoft.cirugia.domain.dto.RecoveryBed;
import com.kynsoft.cirugia.domain.dto.Surgery;
import com.kynsoft.cirugia.domain.enums.SurgeryStatus;
import com.kynsoft.cirugia.domain.service.IBedAssignmentRepository;
import com.kynsoft.cirugia.domain.service.IBedAssignmentService;
import com.kynsoft.cirugia.domain.service.IRecoveryBedService;
import com.kynsoft.cirugia.domain.service.ISurgeryService;
import com.kynsoft.cirugia.infrastructure.entities.SurgeryEntity;
import com.kynsoft.cirugia.infrastructure.repository.command.SurgeryWriteRepository;
import com.kynsoft.cirugia.infrastructure.repository.query.SurgeryReadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChangeSurgeryStatusCommandHandler implements ICommandHandler<ChangeSurgeryStatusCommand> {

    private final ISurgeryService surgeryReadRepository;
    private final IBedAssignmentService bedAssignmentService;
    private final IRecoveryBedService recoveryBedService;
    private final IBedAssignmentRepository bedAssignmentRepository;
    @Override
    @Transactional
    public void handle(ChangeSurgeryStatusCommand command) {
        log.info("Changing surgery {} status to: {}", command.getSurgeryId(), command.getStatus());
        Optional<Surgery> surgery = surgeryReadRepository.getSurgeryById(command.getSurgeryId());
        if(command.getStatus() == SurgeryStatus.DISCHARGED) {
            AtomicReference<BedAssignment> assignmentValue = new AtomicReference<>();
            BedAssignment bedAssignment =  bedAssignmentRepository.findLastActiveAssignmentByBedId(surgery.get().getRecoveryBedEntityId(), surgery.get().getBusinessId());
            if (bedAssignment != null) {
                assignmentValue.set(bedAssignment);
                // Actualizar el estado de la asignación anterior a "COMPLETED"
                bedAssignment.setStatus("COMPLETED");
                bedAssignment.setReleaseDate(LocalDateTime.now());
                bedAssignmentService.update(assignmentValue.get());
            }
            RecoveryBed recoveryBed = recoveryBedService.findById(surgery.get().getRecoveryBedEntityId());
            if (recoveryBed != null) {
                // Actualizar el estado de la cama a "ASSIGNED"
                recoveryBed.setStatus("AVAILABLE");
                recoveryBedService.update(recoveryBed);
            } else {
                log.warn("Cama con ID {} no encontrada para actualizar su estado.", surgery.get().getRecoveryBedEntityId());
            }
        }
        surgeryReadRepository.changeSurgeryStatus(command.getSurgeryId(), command.getStatus().toString(), command.getUpdatedBy());
    }
}