package com.kynsoft.settings.domain.services;


import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.settings.domain.dto.BusinessDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IBusinessService {
    UUID create(BusinessDto business);

    void update(BusinessDto business);

    void delete(UUID id);

    BusinessDto findById(UUID id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}