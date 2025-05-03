package com.kynsoft.cirugia.application.command.diagnosis.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.domain.service.IDiagnosisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteDiagnosisCommandHandler implements ICommandHandler<DeleteDiagnosisCommand> {

    private final IDiagnosisService diagnosisService;

    @Override
    @Transactional
    public void handle(DeleteDiagnosisCommand command) {
        log.info("Deleting diagnosis with ID: {}", command.getDiagnosisId());
        diagnosisService.delete(command.getDiagnosisId());
    }
}