package com.kynsof.payment.controller;

import com.kynsof.payment.domain.service.IPaymentReconciliationService;
import com.kynsof.payment.infrastructure.entity.PaymentReconciliationHeader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/reconciliation")
public class PaymentReconciliationController {


    private final IPaymentReconciliationService paymentReconciliationService;

    public PaymentReconciliationController(IPaymentReconciliationService paymentReconciliationService) {
        this.paymentReconciliationService = paymentReconciliationService;
    }

    @PostMapping("/generate")
    public PaymentReconciliationHeader generateReconciliation(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam UUID businessId) {

        return paymentReconciliationService.reconcilePayments(startDate, endDate, businessId);
    }
}