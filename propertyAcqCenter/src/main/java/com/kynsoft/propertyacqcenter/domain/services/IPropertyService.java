package com.kynsoft.propertyacqcenter.domain.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.dto.projection.PropertyWithProfileDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IPropertyService {

    String create(PropertyDto propertyDto);

    void update(PropertyDto propertyDto);

    void updateBuyerName(String property, LegalEntityDto buyer);

    void delete(String id);

    PropertyDto getById(String id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);

    long countById(String id);

    void validatePropertyId(String id);

    List<PropertyWithProfileDTO> findPropertiesWithProfileByContact(UUID contactId);

    PaginatedResponse searchWithProfileByContact(Pageable pageable, List<FilterCriteria> filterCriteria);
}
