package com.kynsoft.propertyacqcenter.application.command.bankDocument.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.BankAccountDto;
import com.kynsoft.propertyacqcenter.domain.dto.BankDocumentDto;
import com.kynsoft.propertyacqcenter.domain.services.IBankAccountService;
import com.kynsoft.propertyacqcenter.domain.services.IBankDocumentService;
import org.springframework.stereotype.Component;

@Component
public class UpdateBankDocumentCommandHandler implements ICommandHandler<UpdateBankDocumentCommand> {

    private final IBankDocumentService bankDocumentService;
    private final IBankAccountService bankAccountService;

    public UpdateBankDocumentCommandHandler(IBankDocumentService bankDocumentService, IBankAccountService bankAccountService) {
        this.bankDocumentService = bankDocumentService;
        this.bankAccountService = bankAccountService;
    }

    @Override
    public void handle(UpdateBankDocumentCommand command) {
        BankAccountDto bankAccountDto = this.bankAccountService.findById(command.getBankAccount());
        bankDocumentService.update(BankDocumentDto.builder()
                .document(command.getDocument())
                .id(command.getId())
                .fileName(command.getFileName())
                .bankAccount(bankAccountDto)
                .build()
        );
    }
}
