package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.*;
import com.kynsoft.propertyacqcenter.domain.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountAdquisitionPropertyResponse implements IResponse {
    private UUID id;
    private String bankName;
    private String accountNumber;
    private String routingNumber;
    private AccountType accountType;
    private String accountNickname;
    private LocalDate openingDate;
    private String domesticWare;
    private String holderName;

    public BankAccountAdquisitionPropertyResponse(BankAccountDto dto) {
        this.id = dto.getId();
        this.domesticWare = dto.getDomesticWare();
        this.bankName = dto.getBankName();
        this.accountNumber = dto.getAccountNumber();
        this.routingNumber = dto.getRoutingNumber();
        this.accountType = dto.getAccountType();
        this.accountNickname = dto.getAccountNickname();
        this.openingDate = dto.getOpeningDate();
        this.holderName = dto.getHolderName();
    }

}
