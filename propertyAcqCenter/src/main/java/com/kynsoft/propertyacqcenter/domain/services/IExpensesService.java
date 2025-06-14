package com.kynsoft.propertyacqcenter.domain.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.dto.ExpensesDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IExpensesService {
    UUID create(ExpensesDto dto);
    
    void update(ExpensesDto dto);
    
    void delete(UUID id);
    
    ExpensesDto findById(UUID id);
    
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);

    ExpensesDto findByPropertyId(String propertyId);

    boolean existsByPropertyId(String propertyId);
}