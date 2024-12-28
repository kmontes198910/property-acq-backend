package com.kynsof.calendar.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kynsof.calendar.domain.dto.ReceiptDto;
import com.kynsof.calendar.domain.dto.enumType.EStatusReceipt;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Receipt {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private Double price;
    private Boolean express;

    @Size(max = 200)
    private String reasons;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_pk_user")
    private Patient user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_pk_schedule")
    private Schedule schedule;

    @Enumerated(EnumType.STRING)
    private EStatusReceipt status;

    private String consultId;

    private String requestId;
    private String authorizationCode;
    private String reference;
    private String ipAddressCreate;
    private String ipAddressPayment;
    private String userAgentCreate;
    private String userAgentPayment;
    private String processUrl;


    private LocalDateTime paymentDate;

    @Transient
    private EStatusReceipt previousStatus;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;


    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Receipt(ReceiptDto receipt) {
        this.id = receipt.getId();
        this.price = receipt.getPrice();
        this.express = receipt.getExpress();
        this.reasons = receipt.getReasons();
        this.status = receipt.getStatus();
        this.user = new Patient(receipt.getUser());
        this.schedule = receipt.getSchedule() != null ? new Schedule(receipt.getSchedule()) : null;
        this.requestId = receipt.getRequestId();
        this.authorizationCode = receipt.getAuthorizationCode();
        this.reference = receipt.getReference();

        this.ipAddressCreate = receipt.getIpAddressCreate();
        this.ipAddressPayment = receipt.getIpAddressPayment();
        this.userAgentCreate = receipt.getUserAgentCreate();
        this.userAgentPayment = receipt.getUserAgentPayment();
        this.consultId = receipt.getConsultId();
        this.setProcessUrl(receipt.getProcessUrl());
    }

    public ReceiptDto toAggregate() {
        return new ReceiptDto(id, price, express, reasons, user.toAggregate(), schedule.toAggregate(),
                schedule.getService().toAggregate(), status, requestId, authorizationCode, reference, ipAddressCreate,
                ipAddressPayment, userAgentCreate, userAgentPayment,consultId,processUrl);
    }

    @PostLoad
    @PostPersist
    @PostUpdate
    public void onLoad() {
        previousStatus = this.status;
    }

    @PreUpdate
    public void onPreUpdate() {
        if (EStatusReceipt.PAYMENT.equals(status) && !status.equals(previousStatus)) {
            paymentDate = LocalDateTime.now();
        }
    }
}
