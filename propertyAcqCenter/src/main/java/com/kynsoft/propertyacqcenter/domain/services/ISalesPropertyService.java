package com.kynsoft.propertyacqcenter.domain.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.dto.SalesPropertyDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ISalesPropertyService {
    UUID create(SalesPropertyDto address);
    
    void update(SalesPropertyDto address);
    
    void delete(UUID id);
    
    SalesPropertyDto findById(UUID id);
    
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);

    SalesPropertyDto findByPropertyId(String propertyId);
}