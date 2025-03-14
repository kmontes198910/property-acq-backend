package com.kynsof.payment.infrastructure.entity;

import com.kynsof.payment.domain.dto.AccountReconciliationDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "account_reconciliation")
@Getter
@Setter
@NoArgsConstructor
public class AccountReconciliation {

    @Id
    @GeneratedValue
    private UUID id;

    private String code;
    private String description;
    private double cost;

    @ManyToOne
    @JoinColumn(name = "business_id")
    private Business business;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public AccountReconciliation(AccountReconciliationDto dto) {
        this.id = dto.getId();
        this.code = dto.getCode();
        this.description = dto.getDescription();
        this.cost = dto.getCost();
        this.business = dto.getBusiness() != null ? new Business(dto.getBusiness()) : null;
    }

    public AccountReconciliationDto toAggregate() {
        AccountReconciliationDto dto = new AccountReconciliationDto();
        dto.setId(id);
        dto.setCode(code);
        dto.setDescription(description);
        dto.setCost(cost);
        dto.setBusiness(business != null ? business.toAggregate() : null);
        dto.setCreatedAt(createdAt);
        dto.setUpdatedAt(updatedAt);
        return dto;
    }
}