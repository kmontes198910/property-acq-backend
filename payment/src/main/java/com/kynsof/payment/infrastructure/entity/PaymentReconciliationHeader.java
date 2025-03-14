package com.kynsof.payment.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "payment_reconciliation_header")
@Getter
@Setter
@NoArgsConstructor
public class PaymentReconciliationHeader {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false)
    private Long totalPayments;

    @Column(nullable = false)
    private double totalRevenue;

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    private Business business; // Identificación de la empresa

    @Column(name = "user_system_id")
    private UUID userSystemId;

    @Column(name = "user_system_full_name")
    private String userSystemFullName;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "reconciliationHeader", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PaymentReconciliationDetail> details;

    public PaymentReconciliationHeader(LocalDateTime startDate, LocalDateTime endDate, Long totalPayments, double totalRevenue, Business business, UUID userI, String userFullName) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPayments = totalPayments;
        this.totalRevenue = totalRevenue;
        this.business = business;
        this.userSystemId = userI;
        this.userSystemFullName = userFullName;
    }
}