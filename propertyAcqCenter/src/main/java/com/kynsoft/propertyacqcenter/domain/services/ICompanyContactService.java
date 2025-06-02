package com.kynsoft.propertyacqcenter.domain.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyContactDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ICompanyContactService {
    UUID create(CompanyContactDto object);
    
    void update(CompanyContactDto object);
    
    void delete(UUID id);
    
    CompanyContactDto findById(UUID id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);

    long countByEmail(String email, UUID id);

    long countByPersonalEmail(String personalEmail, UUID id);

    void validateEmail(String email, UUID id);

    void validatePersonEmail(String email, UUID id);
}