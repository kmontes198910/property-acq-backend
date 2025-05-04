package com.kynsoft.cirugia.application.command.preOperative.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.domain.dto.PreOperative;
import com.kynsoft.cirugia.domain.service.IPreOperativeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class CreatePreOperativeCommandHandler implements ICommandHandler<CreatePreOperativeCommand> {

    private final IPreOperativeRepository preOperativeRepository;

    public CreatePreOperativeCommandHandler(IPreOperativeRepository preOperativeRepository) {
        this.preOperativeRepository = preOperativeRepository;
    }

    @Override
    public void handle(CreatePreOperativeCommand command) {
        log.info("Creating PreOperative for surgery: {}", command.getSurgeryId());
        UUID id = UUID.randomUUID();
        PreOperative preOperative = PreOperative.builder()
                .id(id)
                .surgeryId(command.getSurgeryId())
                .admissionReason(command.getAdmissionReason())
                .currentDiseaseHistory(command.getCurrentDiseaseHistory())
                .physicalExamination(command.getPhysicalExamination())
                .createdAt(LocalDateTime.now())
                .createdBy(command.getCreatedBy())
                .build();
        
        preOperativeRepository.create(preOperative);
        command.setId(id);
        log.info("PreOperative created with ID: {}", preOperative.getId());
    }
}