package com.kynsof.treatments.domain.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.treatments.domain.dto.GroupPaymentDto;
import com.kynsof.treatments.domain.dto.enumDto.GroupPaymentStatus;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IGroupPaymentService {
    void delete(UUID id);
    UUID createGroupPayment(List<UUID> billingIds, UUID businessId, UUID patientsId);
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);

    GroupPaymentDto findById(UUID id);
    PaginatedResponse searchPaymentDetail(Pageable pageable, List<FilterCriteria> filterCriteria);

    void update(UUID id, String reference, String authorizationCode, String requestId, String processUrl, GroupPaymentStatus status);
}