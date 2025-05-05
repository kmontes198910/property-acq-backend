package com.kynsoft.cirugia.application.command.diagnosis.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.domain.service.IDiagnosisService;
import com.kynsoft.cirugia.domain.dto.Diagnosis;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateDiagnosisCommandHandler implements ICommandHandler<CreateDiagnosisCommand> {

    private final IDiagnosisService diagnosisService;

    @Override
    @Transactional
    public void handle(CreateDiagnosisCommand command) {
        log.info("Creating new diagnosis with ICD code: {}", command.getIcdCode());
        
        Diagnosis diagnosis = Diagnosis.builder()
                .id(command.getId())
                .icdCode(command.getIcdCode())
                .description(command.getDescription())
                .type(command.getType())
                .surgeryId(command.getSurgeryId())
                .createdBy(command.getCreatedBy())
                .build();
        
      Diagnosis diagnosisResponse =  diagnosisService.create(diagnosis);
      command.setId(diagnosisResponse.getId());
    }
}