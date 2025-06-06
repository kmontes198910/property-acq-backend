package com.kynsoft.settings.application.command.recoverybed.create;


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
public class CreateRecoveryBedCommandHandler implements ICommandHandler<CreateRecoveryBedCommand> {

    private final IRecoveryBedService recoveryBedService;
    private final EventRecoveryBedPublisherService recoveryBedPublisherService;

    @Override
    @Transactional
    public void handle(CreateRecoveryBedCommand command) {
        log.info("Creating new recovery bed: {}", command.getBedNumber());

        RecoveryBed recoveryBed = RecoveryBed.builder()
                .bedNumber(command.getBedNumber())
                .location(command.getLocation())
                .type(command.getType())
                .status(command.getStatus())
                .businessId(command.getBusinessId())
                .floor(command.getFloor())
                .recoveryRoomId(command.getRecoveryRoomId())
                .hasMonitor(command.getHasMonitor())
                .hasOxygenSupply(command.getHasOxygenSupply())
                .lastMaintenanceDate(command.getLastMaintenanceDate())
                .createdBy(command.getCreatedBy())
                .build();

        RecoveryBed createdBed = recoveryBedService.create(recoveryBed);
        command.setId(createdBed.getId());
        log.info("Recovery bed created with ID: {}", createdBed.getId());

        recoveryBedPublisherService.publishRecoveryBedEvent(createdBed);
    }
}