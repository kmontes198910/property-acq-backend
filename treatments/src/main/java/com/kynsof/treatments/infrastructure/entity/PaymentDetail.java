package com.kynsof.treatments.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "payment_detail")
@Getter
@Setter
@NoArgsConstructor
public class PaymentDetail {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_payment_id", nullable = false)
    private GroupPayment groupPayment; // Relación con el pago agrupado

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billing_id", nullable = false)
    private Billing billing; // Relación con los pagos pendientes del paciente

    @Column(nullable = false)
    private Double amount; // Monto pagado

    public PaymentDetail(GroupPayment groupPayment, Billing billing, Double amount) {
        this.groupPayment = groupPayment;
        this.billing = billing;
        this.amount = amount;
    }
}