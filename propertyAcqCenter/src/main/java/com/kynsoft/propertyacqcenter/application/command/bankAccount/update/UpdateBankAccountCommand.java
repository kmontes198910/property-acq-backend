package com.kynsoft.propertyacqcenter.application.command.bankAccount.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.application.command.bankAccount.create.InternationalBankingDetailsRequest;
import com.kynsoft.propertyacqcenter.domain.dto.embedded.BankBranchDto;
import com.kynsoft.propertyacqcenter.domain.dto.embedded.BankContactDto;
import com.kynsoft.propertyacqcenter.domain.enums.AccountType;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateBankAccountCommand implements ICommand {

    private UUID id;
    private UUID legalEntity;
    private String bankName;
    private String accountNumber;
    private String routingNumber;
    private AccountType accountType;
    private String accountNickname;
    private LocalDate openingDate;
    private String onlineBankingUrl;
    private String notes;

    private BankContactDto contactDetails;
    private InternationalBankingDetailsRequest internationalDetails;
    private BankBranchDto branchInfo;

    public UpdateBankAccountCommand(UUID id, UUID legalEntity, String bankName, String accountNumber, 
            String routingNumber, AccountType accountType, String accountNickname, 
            LocalDate openingDate, String onlineBankingUrl, String notes, 
            BankContactDto contactDetails, InternationalBankingDetailsRequest internationalDetails, 
            BankBranchDto branchInfo) {
        this.id = id;
        this.legalEntity = legalEntity;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.routingNumber = routingNumber;
        this.accountType = accountType;
        this.accountNickname = accountNickname;
        this.openingDate = openingDate;
        this.onlineBankingUrl = onlineBankingUrl;
        this.notes = notes;
        this.contactDetails = contactDetails;
        this.internationalDetails = internationalDetails;
        this.branchInfo = branchInfo;
    }

    public static UpdateBankAccountCommand fromRequest(UpdateBankAccountRequest request, UUID id) {
        return new UpdateBankAccountCommand(
                id,
                request.getLegalEntity(),
                request.getBankName(),
                request.getAccountNumber(),
                request.getRoutingNumber(),
                request.getAccountType(),
                request.getAccountNickname(),
                request.getOpeningDate(),
                request.getOnlineBankingUrl(),
                request.getNotes(),
                request.getContactDetails(),
                request.getInternationalDetails(),
                request.getBranchInfo()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateBankAccountMessage(id);
    }
}
