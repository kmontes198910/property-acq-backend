package com.kynsoft.propertyacqcenter.domain.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.dto.SubCategoryRealEstateCompanyTypeDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ISubCategoryRealEstateCompanyTypeService {
    UUID create(SubCategoryRealEstateCompanyTypeDto object);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}