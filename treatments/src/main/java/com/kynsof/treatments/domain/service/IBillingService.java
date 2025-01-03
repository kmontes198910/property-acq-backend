package com.kynsof.treatments.domain.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.treatments.domain.dto.BillingDto;
import com.kynsof.treatments.domain.dto.enumDto.BillingStatus;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IBillingService {

    void create(BillingDto dto);
    void createAll(List<BillingDto> dtoList);

    void update(BillingDto billingDto);

    BillingDto findById(UUID id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);

    void delete(BillingDto object);

    UUID createGroupPayment(List<UUID> billingIds);
    boolean existsByCodeAndBusinessIdAndStatusAndPatientId(String code, UUID businessId, BillingStatus status, UUID patientId);

}