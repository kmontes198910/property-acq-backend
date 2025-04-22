package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.BankAccountDto;
import com.kynsoft.propertyacqcenter.infrastructure.entity.enums.AccountType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "bank_accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankAccount {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "legal_entity_id", nullable = false)
    private LegalEntity legalEntity;

    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;
    
    @Column(name = "routing_number", nullable = false)
    private String routingNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private AccountType accountType;

    @Column(name = "account_nickname")
    private String accountNickname;

    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "contact_phone")
    private String contactPhone;
    
    @Column(name = "swift_code")
    private String swiftCode;
    
    @Column(name = "iban")
    private String iban;
    
    @Column(name = "account_currency")
    private String accountCurrency;
    
    @Column(name = "opening_date")
    private LocalDate openingDate;
    
    @Column(name = "branch_name")
    private String branchName;
    
    @Column(name = "branch_address")
    private String branchAddress;
    
    @Column(name = "branch_city")
    private String branchCity;
    
    @Column(name = "branch_state")
    private String branchState;
    
    @Column(name = "branch_zip_code")
    private String branchZipCode;
    
    @Column(name = "online_banking_url")
    private String onlineBankingUrl;
    
    @Column(name = "signatories", columnDefinition = "TEXT")
    private String signatories;
    
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "is_primary")
    private Boolean isPrimary;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "updated_by")
    private UUID updatedBy;

    public BankAccount(BankAccountDto dto, LegalEntity legalEntity) {
        this.id = dto.getId() != null ? dto.getId() : UUID.randomUUID();
        this.legalEntity = legalEntity;
        this.bankName = dto.getBankName();
        this.accountNumber = dto.getAccountNumber();
        this.routingNumber = dto.getRoutingNumber();
        this.accountType = dto.getAccountType();
        this.accountNickname = dto.getAccountNickname();
        this.contactName = dto.getContactName();
        this.contactPhone = dto.getContactPhone();
        this.swiftCode = dto.getSwiftCode();
        this.iban = dto.getIban();
        this.accountCurrency = dto.getAccountCurrency();
        this.openingDate = dto.getOpeningDate();
        this.branchName = dto.getBranchName();
        this.branchAddress = dto.getBranchAddress();
        this.branchCity = dto.getBranchCity();
        this.branchState = dto.getBranchState();
        this.branchZipCode = dto.getBranchZipCode();
        this.onlineBankingUrl = dto.getOnlineBankingUrl();
        this.signatories = dto.getSignatories();
        this.notes = dto.getNotes();
        this.isPrimary = dto.getIsPrimary();
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
    }

    public BankAccountDto toAggregate() {
        return BankAccountDto.builder()
                .id(this.id)
                .legalEntityId(this.legalEntity != null ? this.legalEntity.getId() : null)
                .bankName(this.bankName)
                .accountNumber(this.accountNumber)
                .routingNumber(this.routingNumber)
                .accountType(this.accountType)
                .accountNickname(this.accountNickname)
                .contactName(this.contactName)
                .contactPhone(this.contactPhone)
                .swiftCode(this.swiftCode)
                .iban(this.iban)
                .accountCurrency(this.accountCurrency)
                .openingDate(this.openingDate)
                .branchName(this.branchName)
                .branchAddress(this.branchAddress)
                .branchCity(this.branchCity)
                .branchState(this.branchState)
                .branchZipCode(this.branchZipCode)
                .onlineBankingUrl(this.onlineBankingUrl)
                .signatories(this.signatories)
                .notes(this.notes)
                .isPrimary(this.isPrimary)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .createdBy(this.createdBy)
                .updatedBy(this.updatedBy)
                .build();
    }
}