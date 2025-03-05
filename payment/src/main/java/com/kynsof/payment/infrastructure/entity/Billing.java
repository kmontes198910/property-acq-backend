package com.kynsof.payment.infrastructure.entity;

import com.kynsof.payment.domain.dto.BillingDto;
import com.kynsof.payment.domain.dto.enumDto.BillingStatus;
import com.kynsof.payment.domain.dto.enumDto.TypeOperation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "billing")
@Getter
@Setter
@NoArgsConstructor
public class Billing {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String code;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @Column(nullable = false)
    private Double cost;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BillingStatus status;

    @Enumerated(EnumType.STRING)
    private TypeOperation typeOperation;

    @Column(name = "is_proforma", nullable = false)
    private boolean isProforma;

    @Column(name = "user_system_id")
    private UUID userSystemId;

    @Column(name = "user_system_full_name")
    private String userSystemFullName;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Billing(BillingDto billingDto) {
        this.id = billingDto.getId();
        this.client =  new Client(billingDto.getClient());
        this.business = new Business(billingDto.getBusiness());
        this.code = billingDto.getCode();
        this.description = billingDto.getDescription();
        this.status = billingDto.getStatus();
        this.cost = billingDto.getCost();
        this.isProforma = billingDto.isProforma();
        this.userSystemId = billingDto.getUserSystemId();
        this.userSystemFullName = billingDto.getUserSystemFullName();
        this.typeOperation = billingDto.getTypeOperation();
    }

    public BillingDto toAggregate() {
        BillingDto billingDto = new BillingDto();
        billingDto.setId(this.id);
        billingDto.setCode(this.code);
        billingDto.setClient(this.client.toAggregate());
        billingDto.setBusiness(this.business.toAggregate());
        billingDto.setDescription(this.description);
        billingDto.setStatus(this.status);
        billingDto.setCost(this.cost);
        billingDto.setCreatedAt(this.createdAt);
        billingDto.setProforma(this.isProforma);
        billingDto.setUserSystemId(this.userSystemId);
        billingDto.setUserSystemFullName(this.userSystemFullName);
        billingDto.setTypeOperation(this.typeOperation);
        return billingDto;
    }
}