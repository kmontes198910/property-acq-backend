package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.infrastructure.entity.embeddedEntity.InternationalBankingDetails;
import com.kynsoft.propertyacqcenter.infrastructure.entity.embeddedEntity.BankBranch;
import com.kynsoft.propertyacqcenter.infrastructure.entity.embeddedEntity.BankContact;
import com.kynsoft.propertyacqcenter.domain.dto.BankAccountDto;
import com.kynsoft.propertyacqcenter.domain.dto.embedded.BankBranchDto;
import com.kynsoft.propertyacqcenter.domain.dto.embedded.BankContactDto;
import com.kynsoft.propertyacqcenter.domain.dto.embedded.EmbeddableAddressDto;
import com.kynsoft.propertyacqcenter.domain.dto.embedded.InternationalBankingDetailsDto;
import com.kynsoft.propertyacqcenter.domain.enums.AccountType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency", nullable = false)
    private Currency currency;

    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;
    
    @Column(name = "routing_number", nullable = false)
    private String routingNumber;
    
    @Column(name = "domestic_ware", nullable = true)
    private String domesticWare;

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

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BankDocument> bankDocuments = new HashSet<>();

    @Column(name = "notes",columnDefinition = "TEXT")
    private String notes;

    @Column(name = "holder_name")
    private String holderName;

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

    public BankAccount(BankAccountDto dto) {
        this.domesticWare = dto.getDomesticWare();
        this.id = dto.getId() != null ? dto.getId() : UUID.randomUUID();
        this.legalEntity = dto.getLegalEntity() != null ? new LegalEntity(dto.getLegalEntity()) : null;
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
        this.contactDetails = new BankContact(
                dto.getContactDetails().getName(), 
                dto.getContactDetails().getPhone(), 
                dto.getContactDetails().getEmail()
        );
        this.internationalDetails = new InternationalBankingDetails(
                dto.getInternationalDetails().getSwiftCode(), 
                dto.getInternationalDetails().getIban()
        );
        this.branchInfo = new BankBranch(
                dto.getBranchInfo().getName(), 
                new EmbeddableAddress(
                        dto.getBranchInfo().getAddress().getStreetAddress1(), 
                        dto.getBranchInfo().getAddress().getStreetAddress2(), 
                        dto.getBranchInfo().getAddress().getCity(), 
                        dto.getBranchInfo().getAddress().getState(), 
                        dto.getBranchInfo().getAddress().getZipCode(), 
                        dto.getBranchInfo().getAddress().getCountry(), 
                        dto.getBranchInfo().getAddress().getAddressType()
                ), 
                dto.getBranchInfo().getPhone()
        );
        this.currency = new Currency(dto.getInternationalDetails().getCurrency());
        this.holderName = dto.getHolderName();
    }

    public BankAccountDto toAggregate() {
        return BankAccountDto.builder()
                .id(this.id)
                .domesticWare(domesticWare)
                .bankName(bankName)
                .legalEntity(legalEntity.toAggregateBasic())
                .accountNumber(accountNumber)
                .routingNumber(routingNumber)
                .accountType(accountType)
                .accountNickname(accountNickname)
                .openingDate(openingDate)
                .onlineBankingUrl(onlineBankingUrl)
                .notes(notes)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .createdBy(this.createdBy)
                .updatedBy(this.updatedBy)
                .holderName(holderName)
                .contactDetails(contactDetails != null ? new BankContactDto(contactDetails.getName(), contactDetails.getPhone(), contactDetails.getEmail()) : null)
                .bankDocuments(bankDocuments != null ? bankDocuments.stream().map(bankAccount -> bankAccount.toAggregateBasic()).collect(Collectors.toList()) : null)
                .internationalDetails(internationalDetails != null ?
                        new InternationalBankingDetailsDto(
                                internationalDetails.getSwiftCode(), 
                                internationalDetails.getIban(), 
                                currency.toAggregate()) : null)
                .branchInfo(branchInfo != null ? new BankBranchDto(
                        branchInfo.getName(), 
                        branchInfo.getAddress() != null ? new EmbeddableAddressDto(
                                branchInfo.getAddress().getStreetAddress1(), 
                                branchInfo.getAddress().getStreetAddress2(), 
                                branchInfo.getAddress().getCity(), 
                                branchInfo.getAddress().getState(), 
                                branchInfo.getAddress().getZipCode(), 
                                branchInfo.getAddress().getCountry(), 
                                branchInfo.getAddress().getAddressType()
                        ) : null, 
                        branchInfo.getPhone()) : null)
                .build();
    }

    public BankAccountDto toAggregateSimple() {
        return BankAccountDto.builder()
                .id(this.id)
                .domesticWare(domesticWare)
                .bankName(bankName)
                .legalEntity(legalEntity.toAggregateFindById())
                .accountNumber(accountNumber)
                .routingNumber(routingNumber)
                .accountType(accountType)
                .accountNickname(accountNickname)
                .openingDate(openingDate)
                .onlineBankingUrl(onlineBankingUrl)
                .notes(notes)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .createdBy(this.createdBy)
                .updatedBy(this.updatedBy)
                .holderName(holderName)
                .contactDetails(contactDetails != null ? new BankContactDto(contactDetails.getName(), contactDetails.getPhone(), contactDetails.getEmail()) : null)
                .internationalDetails(internationalDetails != null ? 
                        new InternationalBankingDetailsDto(
                                internationalDetails.getSwiftCode(), 
                                internationalDetails.getIban(), 
                                currency.toAggregate()) : null)
                .branchInfo(branchInfo != null ? new BankBranchDto(
                        branchInfo.getName(), 
                        branchInfo.getAddress() != null ? new EmbeddableAddressDto(
                                branchInfo.getAddress().getStreetAddress1(), 
                                branchInfo.getAddress().getStreetAddress2(), 
                                branchInfo.getAddress().getCity(),
                                branchInfo.getAddress().getState(), 
                                branchInfo.getAddress().getZipCode(), 
                                branchInfo.getAddress().getCountry(), 
                                branchInfo.getAddress().getAddressType()
                        ) : null, 
                        branchInfo.getPhone()) : null)
                .build();
    }

    public BankAccountDto toAggregateToAdquisition() {
        return BankAccountDto.builder()
                .id(this.id)
                .domesticWare(domesticWare)
                .bankName(bankName)
                .accountNumber(accountNumber)
                .routingNumber(routingNumber)
                .accountType(accountType)
                .accountNickname(accountNickname)
                .openingDate(openingDate)
                .holderName(holderName)
                .contactDetails(contactDetails != null ? new BankContactDto(contactDetails.getName(), contactDetails.getPhone(), contactDetails.getEmail()) : null)
                .build();
    }

    public BankAccountDto toAggregateBasic() {
        return BankAccountDto.builder()
                .id(this.id)
                .bankName(bankName)
                .build();
    }

}