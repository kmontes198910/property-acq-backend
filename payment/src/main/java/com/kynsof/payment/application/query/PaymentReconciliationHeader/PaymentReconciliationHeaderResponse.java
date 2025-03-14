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
    private LocalDateTime createdAt;
    private UUID userSystemId;
    private String userSystemFullName;
    private double totalPlacetoPay;
    private double totalCash;
    private double totalTransfer;

    public PaymentReconciliationHeaderResponse(UUID id, LocalDateTime startDate, LocalDateTime endDate,
                                               Long totalPayments, double totalRevenue, LocalDateTime createdAt,
                                               UUID userSystemId, String userSystemFullName, double totalPlacetoPay,
                                               double totalCash, double totalTransfer) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPayments = totalPayments;
        this.totalRevenue = totalRevenue;
        this.createdAt = createdAt;
        this.userSystemId = userSystemId;
        this.userSystemFullName = userSystemFullName;
        this.totalPlacetoPay = totalPlacetoPay;
        this.totalCash = totalCash;
        this.totalTransfer = totalTransfer;
    }
}
