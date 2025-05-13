package com.kynsoft.propertyacqcenter.domain.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.dto.SubCategoryConstructionTypeDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ISubCategoryConstructionTypeService {
    UUID create(SubCategoryConstructionTypeDto object);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}