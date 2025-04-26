package com.kynsoft.propertyacqcenter.application.command.bankAccount.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.BankAccountDto;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.services.IBankAccountService;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import org.springframework.stereotype.Component;

@Component
public class CreateBankAccountCommandHandler implements ICommandHandler<CreateBankAccountCommand> {

    private final IBankAccountService bankAccountService;
    private final ILegalEntityService legalEntityService;

    public CreateBankAccountCommandHandler(IBankAccountService bankAccountService, ILegalEntityService legalEntityService) {
        this.bankAccountService = bankAccountService;
        this.legalEntityService = legalEntityService;
    }

    @Override
    public void handle(CreateBankAccountCommand command) {
        System.err.println("Legal Entity: " + command.getLegalEntity());
        LegalEntityDto legalEntityDto = this.legalEntityService.findById(command.getLegalEntity());
        bankAccountService.create(new BankAccountDto(
                command.getId(), 
                legalEntityDto, 
                command.getBankName(), 
                command.getAccountNumber(), 
                command.getRoutingNumber(), 
                command.getAccountType(), 
                command.getAccountNickname(), 
                command.getOpeningDate(), 
                command.getOnlineBankingUrl(), 
                command.getNotes(),
                null, 
                null, 
                command.getContactDetails(), 
                command.getInternationalDetails(), 
                command.getBranchInfo()
        ));
    }
}
