package com.kynsof.payment.application.query.PaymentReconciliationHeader;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class PaymentReconciliationHeaderResponse {
    private UUID id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long totalPayments;
    private double totalRevenue;
    private LocalDateTime generatedAt;

    public PaymentReconciliationHeaderResponse(UUID id, LocalDateTime startDate, LocalDateTime endDate,
                                               Long totalPayments, double totalRevenue, LocalDateTime generatedAt) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPayments = totalPayments;
        this.totalRevenue = totalRevenue;
        this.generatedAt = generatedAt;

    }
}
