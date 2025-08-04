package com.kynsoft.propertyacqcenter.application.command.bankAccount.update;

import com.kynsoft.propertyacqcenter.application.command.bankAccount.create.InternationalBankingDetailsRequest;
import com.kynsoft.propertyacqcenter.domain.dto.embedded.BankBranchDto;
import com.kynsoft.propertyacqcenter.domain.dto.embedded.BankContactDto;
import com.kynsoft.propertyacqcenter.domain.enums.AccountType;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBankAccountRequest {

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
    private String domesticWare;
    private String holderName;
}
