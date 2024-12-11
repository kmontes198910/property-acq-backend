package com.kynsof.treatments.domain.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.treatments.domain.dto.VitalSignsDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IVitalSignsService {
    void create(VitalSignsDto object);

    void update(VitalSignsDto object);

    void delete(UUID id);
    VitalSignsDto findById(UUID id);
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);

}