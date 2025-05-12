package com.kynsoft.propertyacqcenter.application.command.bankDocument.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.IBankDocumentService;
import org.springframework.stereotype.Component;

@Component
public class DeleteBankDocumentCommandHandler implements ICommandHandler<DeleteBankDocumentCommand> {

    private final IBankDocumentService bankDocumentService;

    public DeleteBankDocumentCommandHandler(IBankDocumentService bankDocumentService) {
        this.bankDocumentService = bankDocumentService;
    }

    @Override
    public void handle(DeleteBankDocumentCommand command) {

        bankDocumentService.delete(command.getId());
    }

}
