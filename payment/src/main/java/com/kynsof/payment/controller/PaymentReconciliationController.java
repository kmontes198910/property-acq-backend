package com.kynsof.payment.controller;

import com.kynsof.payment.application.query.PaymentReconciliationHeader.GetSearchPaymentReconciliationHeaderQuery;
import com.kynsof.payment.domain.service.IPaymentReconciliationService;
import com.kynsof.payment.infrastructure.entity.PaymentReconciliationHeader;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reconciliation")
public class PaymentReconciliationController {

    private final IMediator mediator;
    private final IPaymentReconciliationService paymentReconciliationService;

    public PaymentReconciliationController(IMediator mediator, IPaymentReconciliationService paymentReconciliationService) {
        this.mediator = mediator;
        this.paymentReconciliationService = paymentReconciliationService;
    }

    @PostMapping("/generate")
    public PaymentReconciliationHeader generateReconciliation(@RequestBody PaymentReconciliationRequest request) {
        return paymentReconciliationService.reconcilePayments(request.getStartDate(), request.getEndDate(),request.getBusinessId(),
                request.getUserSystemsId(),request.getUserSystemsFullName());
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchPaymentReconciliationHeaderQuery query = new GetSearchPaymentReconciliationHeaderQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }
}