package com.kynsoft.propertyacqcenter.domain.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.dto.AdquisitionPropertyDto;
import com.kynsoft.propertyacqcenter.domain.dto.embedded.adquisitionProperty.UpdateAdquisitionTitleCompanyDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IAdquisitionPropertyService {
    UUID create(AdquisitionPropertyDto dto);
    
    void update(AdquisitionPropertyDto dto);
    
    void delete(UUID id);
    
    AdquisitionPropertyDto findById(UUID id);
    
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);

    List<AdquisitionPropertyDto> findByPropertyId(String propertyId);

    void updateTitleCompany(UpdateAdquisitionTitleCompanyDto object);

    boolean existsByPropertyId(String propertyId);
}