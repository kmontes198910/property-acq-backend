package com.kynsof.payment.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payment_reconciliation_detail")
@Getter
@Setter
@NoArgsConstructor
public class PaymentReconciliationDetail {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "reconciliation_header_id", nullable = false)
    private PaymentReconciliationHeader reconciliationHeader; // Relación con la cabecera

    @Column(nullable = false)
    private String serviceCode; // Código del servicio

    @Column(nullable = false)
    private String description; // Descripción del servicio

    @Column(nullable = false)
    private double totalAmount; // Total recaudado por este servicio

    @Column(nullable = false)
    private double placetopayAmount; // Monto pagado con PlaceToPay

    @Column(nullable = false)
    private double cashAmount; // Monto pagado en efectivo

    @Column(nullable = false)
    private double transferAmount; // Monto pagado por transferencia

    @Column(nullable = false)
    private int serviceCount; // Cantidad de veces que se pagó este servicio

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public PaymentReconciliationDetail(PaymentReconciliationHeader reconciliationHeader, String serviceCode,
                                       String description, int serviceCount, double totalAmount, double placetopayAmount,
                                       double cashAmount, double transferAmount) {
        this.reconciliationHeader = reconciliationHeader;
        this.serviceCode = serviceCode;
        this.description = description;
        this.serviceCount = serviceCount;
        this.totalAmount = totalAmount;
        this.placetopayAmount = placetopayAmount;
        this.cashAmount = cashAmount;
        this.transferAmount = transferAmount;
    }
}