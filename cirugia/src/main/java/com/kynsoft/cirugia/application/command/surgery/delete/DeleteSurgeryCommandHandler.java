package com.kynsoft.cirugia.application.command.surgery.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.domain.service.ISurgeryService;
import com.kynsoft.cirugia.infrastructure.repository.command.SurgeryWriteRepository;
import com.kynsoft.cirugia.infrastructure.repository.query.SurgeryReadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteSurgeryCommandHandler implements ICommandHandler<DeleteSurgeryCommand> {

    private final ISurgeryService surgeryReadRepository;

    @Override
    @Transactional
    public void handle(DeleteSurgeryCommand command) {
        log.info("Deleting surgery with ID: {}", command.getSurgeryId());
        surgeryReadRepository.deleteSurgery(command.getSurgeryId());
    }
}