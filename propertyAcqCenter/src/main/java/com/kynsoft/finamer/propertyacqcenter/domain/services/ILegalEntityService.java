package com.kynsoft.finamer.propertyacqcenter.domain.services;

import com.kynsoft.finamer.propertyacqcenter.domain.dto.LegalEntityDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ILegalEntityService {
    UUID create(LegalEntityDto legalEntity);
    
    void update(LegalEntityDto legalEntity);
    
    void delete(UUID id);
    
    LegalEntityDto findById(UUID id);
    
    LegalEntityDto findByTaxId(String taxId);
    
    List<LegalEntityDto> search(Pageable pageable, List<Object> filterCriteria);
}