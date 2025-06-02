package com.kynsoft.propertyacqcenter.application.command.analysis.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.IAnalysisService;
import org.springframework.stereotype.Component;

@Component
public class DeleteAnalysisCommandHandler implements ICommandHandler<DeleteAnalysisCommand> {

    private final IAnalysisService analysisService;

    public DeleteAnalysisCommandHandler(IAnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @Override
    public void handle(DeleteAnalysisCommand command) {

        analysisService.delete(command.getId());
    }

}
