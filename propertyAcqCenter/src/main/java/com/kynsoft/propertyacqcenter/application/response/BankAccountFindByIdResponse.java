package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.*;
import com.kynsoft.propertyacqcenter.domain.dto.embedded.BankBranchDto;
import com.kynsoft.propertyacqcenter.domain.dto.embedded.BankContactDto;
import com.kynsoft.propertyacqcenter.domain.dto.embedded.InternationalBankingDetailsDto;
import com.kynsoft.propertyacqcenter.domain.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountFindByIdResponse implements IResponse {
    private UUID id;
    private LegalEntityBasicResponse legalEntity;//
    private String bankName;//
    private String accountNumber;//
    private String routingNumber;//
    private AccountType accountType;//
    private String accountNickname;//
    private LocalDate openingDate;//
    private String onlineBankingUrl;//
    private String notes;//
    private LocalDateTime createdAt;//
    private LocalDateTime updatedAt;//
    private UUID createdBy;//
    private UUID updatedBy;//

    private BankContactDto contactDetails;
    private InternationalBankingDetailsDto internationalDetails;
    private BankBranchDto branchInfo;

    public BankAccountFindByIdResponse(BankAccountDto dto) {
        this.id = dto.getId();
        this.legalEntity = new LegalEntityBasicResponse(dto.getLegalEntity());
        this.bankName = dto.getBankName();
        this.accountNumber = dto.getAccountNumber();
        this.routingNumber = dto.getRoutingNumber();
        this.accountType = dto.getAccountType();
        this.accountNickname = dto.getAccountNickname();
        this.openingDate = dto.getOpeningDate();
        this.onlineBankingUrl = dto.getOnlineBankingUrl();
        this.notes = dto.getNotes();
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
        this.contactDetails = dto.getContactDetails();
        this.internationalDetails = dto.getInternationalDetails();
        this.branchInfo = dto.getBranchInfo();
    }

}
