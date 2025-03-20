package com.kynsof.evaluation.domain.service;

import com.kynsof.evaluation.domain.dto.PatientDto;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IPatientsService {
    UUID create(PatientDto patients);
    UUID update(PatientDto patients);
    void delete(UUID id);
    PatientDto findById(UUID id);
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}