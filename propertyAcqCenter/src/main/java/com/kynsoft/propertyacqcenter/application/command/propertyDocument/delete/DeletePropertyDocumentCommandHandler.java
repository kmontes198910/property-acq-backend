package com.kynsoft.propertyacqcenter.application.command.propertyDocument.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyDocumentService;
import org.springframework.stereotype.Component;

@Component
public class DeletePropertyDocumentCommandHandler implements ICommandHandler<DeletePropertyDocumentCommand> {

    private final IPropertyDocumentService propertyDocumentService;

    public DeletePropertyDocumentCommandHandler(IPropertyDocumentService propertyDocumentService) {
        this.propertyDocumentService = propertyDocumentService;
    }

    @Override
    public void handle(DeletePropertyDocumentCommand command) {
        this.propertyDocumentService.delete(command.getId());
    }
}
