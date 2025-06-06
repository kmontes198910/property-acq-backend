package com.kynsoft.propertyacqcenter.domain.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.dto.ClosingCostsDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IClosingCostsService {

    UUID create(ClosingCostsDto dto);

    void update(ClosingCostsDto dto);

    void delete(UUID id);

    ClosingCostsDto findById(UUID id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);

    ClosingCostsDto findByPropertyId(String propertyId);
}
