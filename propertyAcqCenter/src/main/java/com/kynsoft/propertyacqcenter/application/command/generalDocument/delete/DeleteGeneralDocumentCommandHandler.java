package com.kynsoft.propertyacqcenter.application.command.generalDocument.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.IGeneralDocumentService;
import org.springframework.stereotype.Component;

@Component
public class DeleteGeneralDocumentCommandHandler implements ICommandHandler<DeleteGeneralDocumentCommand> {

    private final IGeneralDocumentService generalDocumentService;

    public DeleteGeneralDocumentCommandHandler(IGeneralDocumentService generalDocumentService) {
        this.generalDocumentService = generalDocumentService;
    }

    @Override
    public void handle(DeleteGeneralDocumentCommand command) {
        this.generalDocumentService.delete(command.getId());
    }
}
