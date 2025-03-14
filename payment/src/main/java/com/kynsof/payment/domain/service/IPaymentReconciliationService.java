package com.kynsof.payment.domain.service;

import com.kynsof.payment.domain.dto.PaymentReconciliationHeaderDto;
import com.kynsof.payment.infrastructure.entity.PaymentReconciliationHeader;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IPaymentReconciliationService {
    PaymentReconciliationHeader reconcilePayments(LocalDateTime startDate, LocalDateTime endDate, UUID businessId);
}
