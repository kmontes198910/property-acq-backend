package com.kynsoft.propertyacqcenter.application.command.bankDocument.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.BankAccountDto;
import com.kynsoft.propertyacqcenter.domain.dto.BankDocumentDto;
import com.kynsoft.propertyacqcenter.domain.services.IBankAccountService;
import com.kynsoft.propertyacqcenter.domain.services.IBankDocumentService;
import org.springframework.stereotype.Component;

@Component
public class CreateBankDocumentCommandHandler implements ICommandHandler<CreateBankDocumentCommand> {

    private final IBankDocumentService bankDocumentService;
    private final IBankAccountService bankAccountService;

    public CreateBankDocumentCommandHandler(IBankDocumentService bankDocumentService, IBankAccountService bankAccountService) {
        this.bankDocumentService = bankDocumentService;
        this.bankAccountService = bankAccountService;
    }

    @Override
    public void handle(CreateBankDocumentCommand command) {
        BankAccountDto bankAccountDto = this.bankAccountService.findById(command.getBankAccount());
        bankDocumentService.create(BankDocumentDto.builder()
                .document(command.getDocument())
                .id(command.getId())
                .fileName(command.getFileName())
                .bankAccount(bankAccountDto)
                .build());
    }
}
