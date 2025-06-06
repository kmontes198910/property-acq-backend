package com.kynsoft.settings.domain.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.settings.domain.dto.AddressDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IAddressService {
    UUID create(AddressDto address);
    
    void update(AddressDto address);
    
    void delete(UUID id);
    
    AddressDto findById(UUID id);
    
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);

}