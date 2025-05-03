package com.kynsoft.cirugia.application.command.recoverybed.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.application.query.recoverybed.getbyid.RecoveryBedResponse;
import com.kynsoft.cirugia.domain.dto.RecoveryBed;
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
public class CreateRecoveryBedCommandHandler implements ICommandHandler<CreateRecoveryBedCommand> {

    private final IRecoveryBedService recoveryBedService;

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
                .room(command.getRoom())
                .hasMonitor(command.getHasMonitor())
                .hasOxygenSupply(command.getHasOxygenSupply())
                .lastMaintenanceDate(command.getLastMaintenanceDate())
                .createdBy(command.getCreatedBy())
                .build();
        
        RecoveryBed createdBed = recoveryBedService.create(recoveryBed);
        command.setId(createdBed.getId());
        log.info("Recovery bed created with ID: {}", createdBed.getId());
    }
}