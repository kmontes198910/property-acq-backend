package com.kynsof.payment.application.query.PaymentReconciliationHeader;

import jakarta.persistence.Column;
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
    private LocalDateTime createdAt;
    private UUID userSystemId;
    private String userSystemFullName;

    public PaymentReconciliationHeaderResponse(UUID id, LocalDateTime startDate, LocalDateTime endDate,
                                               Long totalPayments, double totalRevenue, LocalDateTime createdAt, UUID userSystemId, String userSystemFullName) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPayments = totalPayments;
        this.totalRevenue = totalRevenue;
        this.createdAt = createdAt;
        this.userSystemId = userSystemId;
        this.userSystemFullName = userSystemFullName;
    }
}
