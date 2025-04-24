package com.kynsoft.propertyacqcenter.domain.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ILegalEntityService {
    UUID create(LegalEntityDto legalEntity);
    
    void update(LegalEntityDto legalEntity);
    
    void delete(UUID id);
    
    LegalEntityDto findById(UUID id);
    
    LegalEntityDto findByTaxId(String taxId);
    
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}