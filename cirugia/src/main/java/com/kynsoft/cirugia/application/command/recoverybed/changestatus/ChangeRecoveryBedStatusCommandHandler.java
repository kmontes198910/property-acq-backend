package com.kynsoft.cirugia.application.command.recoverybed.changestatus;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.domain.service.IRecoveryBedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChangeRecoveryBedStatusCommandHandler implements ICommandHandler<ChangeRecoveryBedStatusCommand> {

    private final IRecoveryBedService recoveryBedService;

    @Override
    @Transactional
    public void handle(ChangeRecoveryBedStatusCommand command) {
        log.info("Changing recovery bed {} status to: {}", command.getRecoveryBedId(), command.getStatus());
        
        recoveryBedService.updateStatus(command.getRecoveryBedId(), command.getStatus());
    }
}