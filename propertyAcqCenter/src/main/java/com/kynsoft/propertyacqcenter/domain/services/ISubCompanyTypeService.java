package com.kynsoft.propertyacqcenter.domain.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.dto.SubCompanyTypeDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ISubCompanyTypeService {
    UUID create(SubCompanyTypeDto dto);
    
    void update(SubCompanyTypeDto dto);
    
    void delete(UUID id);
    
    SubCompanyTypeDto findById(UUID id);
    
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}