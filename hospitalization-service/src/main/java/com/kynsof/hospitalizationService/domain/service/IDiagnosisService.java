package com.kynsof.hospitalizationService.domain.service;

import com.kynsof.hospitalizationService.domain.dto.DiagnosisDto;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IDiagnosisService {
    UUID create(DiagnosisDto object);

    UUID update(DiagnosisDto object);

    void delete(UUID id);

    DiagnosisDto findById(UUID id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}