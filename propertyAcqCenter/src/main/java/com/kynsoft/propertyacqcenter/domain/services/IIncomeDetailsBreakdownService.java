package com.kynsoft.propertyacqcenter.domain.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.dto.IncomeDetailsBreakdownDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IIncomeDetailsBreakdownService {

    UUID create(IncomeDetailsBreakdownDto address);

    void update(IncomeDetailsBreakdownDto address);

    void delete(UUID id);

    IncomeDetailsBreakdownDto findById(UUID id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}
