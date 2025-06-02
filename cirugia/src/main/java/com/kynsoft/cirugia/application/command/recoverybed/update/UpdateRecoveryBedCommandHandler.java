package com.kynsoft.cirugia.application.command.recoverybed.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.application.query.recoverybed.getbyid.RecoveryBedResponse;
import com.kynsoft.cirugia.domain.dto.RecoveryBed;
import com.kynsoft.cirugia.domain.service.IRecoveryBedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateRecoveryBedCommandHandler implements ICommandHandler<UpdateRecoveryBedCommand> {

    private final IRecoveryBedService recoveryBedService;

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

    }
}