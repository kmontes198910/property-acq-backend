package com.kynsof.treatments.infrastructure.entity;

import com.kynsof.treatments.domain.dto.BillingDto;
import com.kynsof.treatments.domain.dto.enumDto.BillingStatus;
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
    @JoinColumn(name = "patient_id", nullable = false)
    private Patients patient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "insurance_id")
    private Insurance insurance;

    @Column(nullable = false)
    private Double cost;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BillingStatus status;

    @Column(name = "is_proforma", nullable = false)
    private boolean isProforma;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Billing(String code, Patients patient, Business business, Double cost, String description,
                   BillingStatus status, boolean isProforma) {
        this.code = code;
        this.patient = patient;
        this.business = business;
        this.cost = cost;
        this.description = description;
        this.status = status;
        this.isProforma = isProforma;
    }

    public Billing(BillingDto billingDto) {
        this.id = billingDto.getId();
        this.patient =  new Patients(billingDto.getPatient());
        this.business = new Business(billingDto.getBusiness());
        this.code = billingDto.getCode();
        this.description = billingDto.getDescription();
        this.status = billingDto.getStatus();
        this.cost = billingDto.getCost();
        this.isProforma = billingDto.isProforma();
        this.insurance = billingDto.getInsurance() == null ? null : new Insurance(billingDto.getInsurance());
    }

    public BillingDto toAggregate() {
        BillingDto billingDto = new BillingDto();
        billingDto.setId(this.id);
        billingDto.setCode(this.code);
        billingDto.setPatient(this.patient.toAggregate());
        billingDto.setBusiness(this.business.toAggregate());
        billingDto.setDescription(this.description);
        billingDto.setStatus(this.status);
        billingDto.setCost(this.cost);
        billingDto.setCreatedAt(this.createdAt);
        billingDto.setProforma(this.isProforma);
        if (this.insurance != null) {
            billingDto.setInsurance(this.insurance.toAggregate());
        }
        return billingDto;
    }
}