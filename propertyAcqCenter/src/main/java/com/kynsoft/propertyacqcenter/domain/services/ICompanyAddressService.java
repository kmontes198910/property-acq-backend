package com.kynsoft.propertyacqcenter.domain.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyAddressDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ICompanyAddressService {
    UUID create(CompanyAddressDto object);
    
    void update(CompanyAddressDto object);
    
    void delete(UUID id);
    
    CompanyAddressDto findById(UUID id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);

    int countByCompanyAddressAndIsPrimary(UUID company, UUID id);

    void validateAccountNumber(UUID legalEntity, UUID id);
}