package com.kynsoft.propertyacqcenter.domain.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.dto.BankAccountDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IBankAccountService {
    UUID create(BankAccountDto dto);
    
    void update(BankAccountDto dto);
    
    void delete(UUID id);
    
    BankAccountDto findById(UUID id);
    
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);

    int countByLegalEntityAndAccountNumber(UUID legalEntity, String accountNumber, UUID id);

    void validateAccountNumber(UUID legalEntity, String accountNumber, UUID id);
}