package com.kynsof.hospitalizationService.domain.service;

import com.kynsof.hospitalizationService.domain.dto.BedDto;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IBedService {
    UUID create(BedDto object);

    UUID update(BedDto object);

    void delete(UUID id);

    BedDto findById(UUID id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}