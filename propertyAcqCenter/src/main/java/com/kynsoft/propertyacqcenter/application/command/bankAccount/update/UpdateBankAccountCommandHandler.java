package com.kynsoft.propertyacqcenter.application.command.bankAccount.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.BankAccountDto;
import com.kynsoft.propertyacqcenter.domain.dto.CurrencyDto;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.dto.embedded.InternationalBankingDetailsDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.bankAccount.BankAccountLegalEntityRoutingNumberException;
import com.kynsoft.propertyacqcenter.domain.services.IBankAccountService;
import com.kynsoft.propertyacqcenter.domain.services.ICurrencyService;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class UpdateBankAccountCommandHandler implements ICommandHandler<UpdateBankAccountCommand> {

    private final IBankAccountService bankAccountService;
    private final ILegalEntityService legalEntityService;
    private final ICurrencyService currencyService;

    public UpdateBankAccountCommandHandler(IBankAccountService bankAccountService,
            ILegalEntityService legalEntityService,
            ICurrencyService currencyService) {
        this.bankAccountService = bankAccountService;
        this.legalEntityService = legalEntityService;
        this.currencyService = currencyService;
    }

    @Override
    public void handle(UpdateBankAccountCommand command) {
        this.validateRoutingNumber(command.getRoutingNumber());

        if (command.getDomesticWare() != null) {
            this.validateDomesticWare(command.getDomesticWare());
        }

        LegalEntityDto legalEntityDto = this.legalEntityService.findById(command.getLegalEntity());
        CurrencyDto currencyDto = this.currencyService.findById(command.getInternationalDetails().getCurrency());

        this.bankAccountService.validateAccountNumber(command.getLegalEntity(), command.getAccountNumber(), command.getId());

        bankAccountService.update(new BankAccountDto(
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
                command.getBranchInfo(),
                command.getDomesticWare()
        ));
    }

    private void validateRoutingNumber(String routingNumber) {
        Pattern ROUTING_PATTERN = Pattern.compile("^\\d{9}$");
        if (!ROUTING_PATTERN.matcher(routingNumber).matches()) {
            throw new BankAccountLegalEntityRoutingNumberException();
        }
    }

    private void validateDomesticWare(String domesticWare) {
        Pattern ROUTING_PATTERN = Pattern.compile("^\\d{9}$");
        if (!ROUTING_PATTERN.matcher(domesticWare).matches()) {
            throw new BankAccountLegalEntityRoutingNumberException();
        }
    }
}
