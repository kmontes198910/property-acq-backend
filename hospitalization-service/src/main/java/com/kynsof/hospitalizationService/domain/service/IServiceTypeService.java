package com.kynsof.hospitalizationService.domain.service;


import com.kynsof.hospitalizationService.domain.dto.ServiceTypeDto;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IServiceTypeService {

    ServiceTypeDto create(ServiceTypeDto object);

    ServiceTypeDto update(ServiceTypeDto object);

    void delete(UUID id);

    ServiceTypeDto findById(UUID id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);

    Long countByNameAndNotId(String name, UUID id);

    Long countByCodeAndNotId(String code, UUID id);

    List<ServiceTypeDto> findAllToReplicate();
}