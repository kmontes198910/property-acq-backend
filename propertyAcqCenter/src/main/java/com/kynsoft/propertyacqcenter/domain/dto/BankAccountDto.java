package com.kynsoft.propertyacqcenter.domain.dto;

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
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDto {
    private UUID id;
    private LegalEntityDto legalEntity;//
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
    private List<BankDocumentDto> bankDocuments;
    private String domesticWare;
    private String holderName;

    public BankAccountDto(UUID id, LegalEntityDto legalEntity, String bankName, String accountNumber, 
            String routingNumber, AccountType accountType, String accountNickname, LocalDate openingDate, 
            String onlineBankingUrl, String notes, UUID createdBy, UUID updatedBy, BankContactDto contactDetails, 
            InternationalBankingDetailsDto internationalDetails, BankBranchDto branchInfo, String domesticWare,
            String holderName) {
        this.id = id;
        this.domesticWare = domesticWare;
        this.legalEntity = legalEntity;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.routingNumber = routingNumber;
        this.accountType = accountType;
        this.accountNickname = accountNickname;
        this.openingDate = openingDate;
        this.onlineBankingUrl = onlineBankingUrl;
        this.notes = notes;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.contactDetails = contactDetails;
        this.internationalDetails = internationalDetails;
        this.branchInfo = branchInfo;
        this.holderName = holderName;
    }

}
