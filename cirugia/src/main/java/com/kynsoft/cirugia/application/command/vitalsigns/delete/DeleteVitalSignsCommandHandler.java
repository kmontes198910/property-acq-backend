package com.kynsoft.cirugia.application.command.vitalsigns.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.domain.dto.VitalSigns;
import com.kynsoft.cirugia.domain.service.IVitalSignsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeleteVitalSignsCommandHandler implements ICommandHandler<DeleteVitalSignsCommand> {

    private final IVitalSignsService vitalSignsService;

    @Override
    public void handle(DeleteVitalSignsCommand command) {
        log.info("Deleting vital signs with ID: {}", command.getId());
        
        Optional<VitalSigns> vitalSignsOptional = vitalSignsService.findById(command.getId());
        
        if (vitalSignsOptional.isEmpty()) {
            throw new RuntimeException("Vital signs not found with ID: " + command.getId());
        }
        
        vitalSignsService.delete(command.getId());

    }
}