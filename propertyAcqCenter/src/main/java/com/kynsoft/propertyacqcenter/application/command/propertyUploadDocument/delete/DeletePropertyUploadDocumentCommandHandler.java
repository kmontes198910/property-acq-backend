package com.kynsoft.propertyacqcenter.application.command.propertyUploadDocument.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyUploadDocumentService;
import org.springframework.stereotype.Component;

@Component
public class DeletePropertyUploadDocumentCommandHandler implements ICommandHandler<DeletePropertyUploadDocumentCommand> {

    private final IPropertyUploadDocumentService propertyUploadDocumentService;

    public DeletePropertyUploadDocumentCommandHandler(IPropertyUploadDocumentService propertyUploadDocumentService) {
        this.propertyUploadDocumentService = propertyUploadDocumentService;
    }

    @Override
    public void handle(DeletePropertyUploadDocumentCommand command) {
        this.propertyUploadDocumentService.delete(command.getId());
    }
}
