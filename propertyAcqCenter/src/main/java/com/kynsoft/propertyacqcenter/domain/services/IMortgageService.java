package com.kynsoft.propertyacqcenter.domain.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.dto.MortgageDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IMortgageService {

    UUID create(MortgageDto dto);

    void update(MortgageDto dto);

    void delete(UUID id);

    MortgageDto findById(UUID id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}
