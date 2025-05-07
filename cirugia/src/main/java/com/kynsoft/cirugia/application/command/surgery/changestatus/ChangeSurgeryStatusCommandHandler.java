package com.kynsoft.cirugia.application.command.surgery.changestatus;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.domain.service.ISurgeryService;
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

    private final ISurgeryService surgeryReadRepository;

    @Override
    @Transactional
    public void handle(ChangeSurgeryStatusCommand command) {
        log.info("Changing surgery {} status to: {}", command.getSurgeryId(), command.getStatus());
        surgeryReadRepository.changeSurgeryStatus(command.getSurgeryId(), command.getStatus().toString(), command.getUpdatedBy());
    }
}