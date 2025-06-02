package com.kynsoft.cirugia.application.command.recoverybed.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.domain.service.IRecoveryBedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteRecoveryBedCommandHandler implements ICommandHandler<DeleteRecoveryBedCommand> {

    private final IRecoveryBedService recoveryBedService;

    @Override
    @Transactional
    public void handle(DeleteRecoveryBedCommand command) {
        log.info("Deleting recovery bed with ID: {}", command.getRecoveryBedId());
        
        recoveryBedService.delete(command.getRecoveryBedId());
    }
}