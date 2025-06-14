package com.kynsoft.propertyacqcenter.domain.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.dto.IncomeDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IIncomeService {

    UUID create(IncomeDto dto);

    void update(IncomeDto dto);

    void delete(UUID id);

    IncomeDto findById(UUID id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);

    IncomeDto findByPropertyId(String propertyId);

    boolean existsByPropertyId(String propertyId);
}
