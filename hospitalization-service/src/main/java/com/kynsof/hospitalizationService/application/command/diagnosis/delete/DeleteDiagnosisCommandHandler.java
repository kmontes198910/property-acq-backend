package com.kynsof.hospitalizationService.application.command.diagnosis.delete;

import com.kynsof.hospitalizationService.domain.service.IDiagnosisService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class DeleteDiagnosisCommandHandler implements ICommandHandler<DeleteDiagnosisCommand> {

    private final IDiagnosisService serviceImpl;

    public DeleteDiagnosisCommandHandler(IDiagnosisService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public void handle(DeleteDiagnosisCommand command) {

        serviceImpl.delete(command.getId());
    }

}
