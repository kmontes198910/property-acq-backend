package com.kynsof.treatments.domain.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.treatments.domain.dto.BillingDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IBillingService {

    void create(BillingDto medicines);

    void update(BillingDto medicines);

    BillingDto findById(UUID id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);

    void delete(BillingDto object);

}