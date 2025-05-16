package com.kynsoft.propertyacqcenter.application.command.bankAccount.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.BankAccountDto;
import com.kynsoft.propertyacqcenter.domain.dto.CurrencyDto;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.dto.embedded.InternationalBankingDetailsDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.bankAccount.BankAccountLegalEntityAccountNumberException;
import com.kynsoft.propertyacqcenter.domain.services.IBankAccountService;
import com.kynsoft.propertyacqcenter.domain.services.ICurrencyService;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import org.springframework.stereotype.Component;

@Component
public class CreateBankAccountCommandHandler implements ICommandHandler<CreateBankAccountCommand> {

    private final IBankAccountService bankAccountService;
    private final ILegalEntityService legalEntityService;
    private final ICurrencyService currencyService;

    public CreateBankAccountCommandHandler(IBankAccountService bankAccountService, 
                                           ILegalEntityService legalEntityService,
                                           ICurrencyService currencyService) {
        this.bankAccountService = bankAccountService;
        this.legalEntityService = legalEntityService;
        this.currencyService = currencyService;
    }

    @Override
    public void handle(CreateBankAccountCommand command) {
        LegalEntityDto legalEntityDto = this.legalEntityService.findById(command.getLegalEntity());

        this.validateAccountNumber(command);

        CurrencyDto currencyDto = this.currencyService.findById(command.getInternationalDetails().getCurrency());
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
                new InternationalBankingDetailsDto(
                        command.getInternationalDetails().getSwiftCode(), 
                        command.getInternationalDetails().getIban(), 
                        currencyDto
                ), 
                command.getBranchInfo()
        ));
    }

    private void validateAccountNumber(CreateBankAccountCommand command) {
        int count = this.bankAccountService.countByLegalEntityAndAccountNumber(command.getLegalEntity(), command.getAccountNumber());
        if (count > 0) {
            throw new BankAccountLegalEntityAccountNumberException(command.getAccountNumber());
        }
    }
}
