package com.kynsof.payment.controller;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class PaymentReconciliationRequest {
    private LocalDateTime startDate;
    private  LocalDateTime endDate;
    private UUID businessId;

}
