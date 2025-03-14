package com.kynsof.payment.domain.service;

import com.kynsof.payment.infrastructure.entity.PaymentReconciliationHeader;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface IPaymentReconciliationService {
    PaymentReconciliationHeader reconcilePayments(LocalDateTime startDate, LocalDateTime endDate, UUID businessId, UUID userId, String userFullName);
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}
