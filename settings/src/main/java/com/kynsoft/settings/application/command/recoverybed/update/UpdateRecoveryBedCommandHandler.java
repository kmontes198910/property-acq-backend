package com.kynsoft.settings.application.command.recoverybed.update;


import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.settings.domain.dto.RecoveryBed;
import com.kynsoft.settings.domain.services.IRecoveryBedService;
import com.kynsoft.settings.infrastructure.services.rabbitMq.eventPublisher.EventRecoveryBedPublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateRecoveryBedCommandHandler implements ICommandHandler<UpdateRecoveryBedCommand> {

    private final IRecoveryBedService recoveryBedService;
    private final EventRecoveryBedPublisherService recoveryBedPublisherService;

    @Override
    @Transactional
    public void handle(UpdateRecoveryBedCommand command) {
        log.info("Updating recovery bed with ID: {}", command.getId());

        RecoveryBed existingBed = recoveryBedService.findById(command.getId());
        if (existingBed == null) {
            log.error("Recovery bed not found with ID: {}", command.getId());
            throw new RuntimeException("Recovery Bed not found");
        }
        RecoveryBed recoveryBed = RecoveryBed.builder()
                .id(command.getId())
                .bedNumber(command.getBedNumber())
                .location(command.getLocation())
                .type(command.getType())
                .status(command.getStatus())
                .businessId(command.getBusinessId())
                .floor(command.getFloor())
                .hasMonitor(command.getHasMonitor())
                .hasOxygenSupply(command.getHasOxygenSupply())
                .lastMaintenanceDate(command.getLastMaintenanceDate())
                .recoveryRoomId(command.getRecoveryRoomId())
                .createdAt(existingBed.getCreatedAt())
                .createdBy(existingBed.getCreatedBy())
                .updatedBy(command.getUpdatedBy())
                .build();

        RecoveryBed updatedBed = recoveryBedService.update(recoveryBed);
        this.recoveryBedPublisherService.publishRecoveryBedEvent(updatedBed);

    }
}