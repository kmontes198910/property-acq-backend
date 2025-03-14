package com.kynsof.payment.infrastructure.entity;

import com.kynsof.payment.domain.dto.GroupPaymentDetailDto;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_payment_id", nullable = false)
    private GroupPayment groupPayment; // Relación con el pago agrupado

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "billing_id", nullable = false)
    private Billing billing; // Relación con la factura que se esta pagando.

    @Column(nullable = false)
    private Double amount; // Monto pagado

    public PaymentDetail(GroupPayment groupPayment, Billing billing, Double amount) {
        this.groupPayment = groupPayment;
        this.billing = billing;
        this.amount = amount;
    }

    public GroupPaymentDetailDto toAggregate() {
        GroupPaymentDetailDto dto = new GroupPaymentDetailDto();
        dto.setId(id);
        dto.setCode(billing.getCode());
        dto.setDescription(billing.getDescription());
        dto.setCost(billing.getCost());
        dto.setCreatedAt(billing.getCreatedAt());
        return dto;
    }
}