package com.kynsof.hospitalizationService.domain.service;

import com.kynsof.hospitalizationService.domain.dto.VitalSignsDto;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IVitalSignsService {
    UUID create(VitalSignsDto patients);

    UUID update(VitalSignsDto patients);

    void delete(UUID id);

    VitalSignsDto findById(UUID id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}