package com.kynsoft.cirugia.application.command.changestatus;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.domain.model.Surgery;
import com.kynsoft.cirugia.infrastructure.entities.SurgeryEntity;
import com.kynsoft.cirugia.infrastructure.repository.command.SurgeryWriteRepository;
import com.kynsoft.cirugia.infrastructure.repository.query.SurgeryReadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChangeSurgeryStatusCommandHandler implements ICommandHandler<ChangeSurgeryStatusCommand> {

    private final SurgeryReadRepository surgeryReadRepository;
    private final SurgeryWriteRepository surgeryWriteRepository;

    @Override
    @Transactional
    public void handle(ChangeSurgeryStatusCommand command) {
        log.info("Changing surgery {} status to: {}", command.getSurgeryId(), command.getStatus());
        
        SurgeryEntity entity = surgeryReadRepository.findById(command.getSurgeryId())
                .orElseThrow(() -> new RuntimeException("Surgery not found with ID: " + command.getSurgeryId()));
        
        entity.setStatus(command.getStatus());
        entity.setUpdatedAt(LocalDateTime.now());
        
        // If the status is COMPLETED, set the performed date
        if (command.getStatus().equals("COMPLETED")) {
            entity.setPerformedDate(LocalDateTime.now());
        }

        
        entity = surgeryWriteRepository.save(entity);
        command.setId(entity.getId()); // Assign the generated ID from the database to the command
    }
}