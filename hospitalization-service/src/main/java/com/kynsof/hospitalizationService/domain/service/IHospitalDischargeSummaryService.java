package com.kynsof.hospitalizationService.domain.service;

import com.kynsof.hospitalizationService.domain.dto.HospitalDischargeSummaryDto;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IHospitalDischargeSummaryService {
    UUID create(HospitalDischargeSummaryDto object);

    UUID update(HospitalDischargeSummaryDto object);

    void delete(UUID id);

    HospitalDischargeSummaryDto findById(UUID id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}