package com.kynsoft.propertyacqcenter.application.command.bankAccount.create;

import com.kynsoft.propertyacqcenter.domain.dto.embedded.BankBranchDto;
import com.kynsoft.propertyacqcenter.domain.dto.embedded.BankContactDto;
import com.kynsoft.propertyacqcenter.domain.dto.embedded.InternationalBankingDetailsDto;
import com.kynsoft.propertyacqcenter.domain.enums.AccountType;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBankAccountRequest {

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
    private InternationalBankingDetailsDto internationalDetails;
    private BankBranchDto branchInfo;
}
