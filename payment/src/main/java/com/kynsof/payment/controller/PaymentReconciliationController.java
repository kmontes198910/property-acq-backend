package com.kynsof.payment.controller;

import com.kynsof.payment.domain.service.IPaymentReconciliationService;
import com.kynsof.payment.infrastructure.entity.PaymentReconciliationHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reconciliation")
public class PaymentReconciliationController {


    private final IPaymentReconciliationService paymentReconciliationService;

    public PaymentReconciliationController(IPaymentReconciliationService paymentReconciliationService) {
        this.paymentReconciliationService = paymentReconciliationService;
    }

    @PostMapping("/generate")
    public PaymentReconciliationHeader generateReconciliation(@RequestBody PaymentReconciliationRequest request) {
        return paymentReconciliationService.reconcilePayments(request.getStartDate(), request.getEndDate(),request.getBusinessId());
    }
}