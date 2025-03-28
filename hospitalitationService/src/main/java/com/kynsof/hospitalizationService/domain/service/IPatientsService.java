package com.kynsof.hospitalizationService.domain.service;

import com.kynsof.hospitalizationService.domain.dto.PatientDto;

import java.util.UUID;

public interface IPatientsService {
    UUID create(PatientDto patients);
    UUID update(PatientDto patients);
    void delete(UUID id);
    PatientDto findById(UUID id);
//    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}