package com.kynsoft.propertyacqcenter.application.command.document.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.IDocumentService;
import org.springframework.stereotype.Component;

@Component
public class DeleteDocumentCommandHandler implements ICommandHandler<DeleteDocumentCommand> {

    private final IDocumentService documentService;

    public DeleteDocumentCommandHandler(IDocumentService documentService) {
        this.documentService = documentService;
    }

    @Override
    public void handle(DeleteDocumentCommand command) {
        this.documentService.delete(command.getId());
    }
}
