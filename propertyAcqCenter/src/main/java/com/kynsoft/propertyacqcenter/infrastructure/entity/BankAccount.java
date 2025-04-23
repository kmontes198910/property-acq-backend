package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.BankAccountDto;
import com.kynsoft.propertyacqcenter.domain.enums.AccountType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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

    @Embedded
    private BankContact contactDetails;

    @Embedded
    private InternationalBankingDetails internationalDetails;

    @Embedded
    private BankBranch branchInfo;

    @Column(name = "online_banking_url")
    private String onlineBankingUrl;

    @Column(name = "opening_date")
    private LocalDate openingDate;

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BankSignatory> signatories;

    @Lob
    @Column(name = "notes")
    private String notes;

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
        this.openingDate = dto.getOpeningDate();
        this.onlineBankingUrl = dto.getOnlineBankingUrl();
        this.notes = dto.getNotes();
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
    }
}