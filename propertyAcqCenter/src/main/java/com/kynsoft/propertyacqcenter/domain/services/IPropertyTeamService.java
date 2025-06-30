package com.kynsoft.propertyacqcenter.domain.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyTeamDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IPropertyTeamService {

    UUID create(PropertyTeamDto dto);

    void create(List<PropertyTeamDto> dtos);

    void update(PropertyTeamDto dto);

    void delete(UUID id);

    PropertyTeamDto findById(UUID id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);

    PropertyTeamDto findByPropertyId(String propertyId);
}
