package com.kynsof.calendar.domain.service;

import com.kynsof.calendar.domain.dto.ReceiptDto;
import com.kynsof.calendar.infrastructure.entity.Receipt;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface IReceiptService {
    UUID  create(ReceiptDto object);
    void delete(UUID id);
    ReceiptDto findById(UUID id);
    void update(ReceiptDto receipt);
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
    Optional<Receipt> findByRequestId(String requestId);
    List<Map<String, Object>> getAppointmentsByStatus(UUID businessId);
}