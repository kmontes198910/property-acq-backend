package com.kynsoft.propertyacqcenter.application.command.ownerDocument.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.IOwnerDocumentService;
import org.springframework.stereotype.Component;

@Component
public class DeleteOwnerDocumentCommandHandler implements ICommandHandler<DeleteOwnerDocumentCommand> {

    private final IOwnerDocumentService ownerDocumentService;

    public DeleteOwnerDocumentCommandHandler(IOwnerDocumentService ownerDocumentService) {
        this.ownerDocumentService = ownerDocumentService;
    }

    @Override
    public void handle(DeleteOwnerDocumentCommand command) {
        this.ownerDocumentService.delete(command.getId());
    }
}
