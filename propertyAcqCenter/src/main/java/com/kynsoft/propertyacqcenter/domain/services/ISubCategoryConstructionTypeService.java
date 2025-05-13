package com.kynsoft.propertyacqcenter.domain.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISubCategoryConstructionTypeService {
    void create();

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}