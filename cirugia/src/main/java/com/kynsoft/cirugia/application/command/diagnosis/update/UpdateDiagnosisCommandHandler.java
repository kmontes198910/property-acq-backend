package com.kynsoft.cirugia.application.command.diagnosis.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsoft.cirugia.domain.service.IDiagnosisService;
import com.kynsoft.cirugia.domain.dto.Diagnosis;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateDiagnosisCommandHandler implements ICommandHandler<UpdateDiagnosisCommand> {

    private final IDiagnosisService diagnosisService;

    @Override
    @Transactional
    public void handle(UpdateDiagnosisCommand command) {
        log.info("Updating diagnosis with ID: {}", command.getId());
        
        Optional<Diagnosis> existingDiagnosis = diagnosisService.findById(command.getId());
        
        if (existingDiagnosis.isEmpty()) {
            throw new BusinessNotFoundException(
                    new com.kynsof.share.core.domain.exception.GlobalBusinessException(
                            DomainErrorMessage.BUSINESS_NOT_FOUND,
                            new ErrorField("id", "Diagnosis not found with ID: " + command.getId())));
        }
        
        Diagnosis diagnosis = existingDiagnosis.get();
        diagnosis.setIcdCode(command.getIcdCode());
        diagnosis.setDescription(command.getDescription());
        diagnosis.setType(command.getType());
        diagnosis.setSurgeryId(command.getSurgeryId());
        diagnosis.setUpdatedBy(command.getUpdatedBy());
        
        diagnosisService.update(diagnosis);
    }
}