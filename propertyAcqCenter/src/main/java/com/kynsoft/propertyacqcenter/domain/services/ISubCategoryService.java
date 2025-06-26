package com.kynsoft.propertyacqcenter.domain.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.dto.SubCategoryDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ISubCategoryService {
    void create(SubCategoryDto dto);

    void create();

    SubCategoryDto findById(UUID id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}