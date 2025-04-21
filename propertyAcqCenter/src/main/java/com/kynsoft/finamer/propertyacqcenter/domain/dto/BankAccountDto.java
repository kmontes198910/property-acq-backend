package com.kynsoft.finamer.propertyacqcenter.domain.dto;

import com.kynsoft.finamer.propertyacqcenter.infrastructure.entity.enums.AccountType;
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
public class BankAccountDto {
    private UUID id;
    private UUID legalEntityId;
    private String bankName;
    private String accountNumber;
    private String routingNumber;
    private AccountType accountType;
    private String accountNickname;
    private String contactName;
    private String contactPhone;
    private String swiftCode;
    private String iban;
    private String accountCurrency;
    private LocalDate openingDate;
    private String branchName;
    private String branchAddress;
    private String branchCity;
    private String branchState;
    private String branchZipCode;
    private String onlineBankingUrl;
    private String signatories;
    private String notes;
    private Boolean isPrimary;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
}
