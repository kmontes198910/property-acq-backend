package com.kynsoft.propertyacqcenter.domain.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyTypeDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ICompanyTypeService {
    UUID create(CompanyTypeDto companyTypeDto);
    void update(CompanyTypeDto companyTypeDto);
    void delete(UUID id);
    CompanyTypeDto findById(UUID id);
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}
