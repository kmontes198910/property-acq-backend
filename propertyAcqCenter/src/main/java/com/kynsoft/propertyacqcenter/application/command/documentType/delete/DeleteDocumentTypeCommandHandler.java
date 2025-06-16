package com.kynsoft.propertyacqcenter.application.command.documentType.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.IDocumentTypeService;
import org.springframework.stereotype.Component;

@Component
public class DeleteDocumentTypeCommandHandler implements ICommandHandler<DeleteDocumentTypeCommand> {

    private final IDocumentTypeService documentTypeService;

    public DeleteDocumentTypeCommandHandler(IDocumentTypeService documentTypeService) {
        this.documentTypeService = documentTypeService;
    }

    @Override
    public void handle(DeleteDocumentTypeCommand command) {
        this.documentTypeService.delete(command.getId());
    }
}
