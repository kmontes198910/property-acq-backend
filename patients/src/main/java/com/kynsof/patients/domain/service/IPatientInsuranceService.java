package com.kynsof.patients.domain.service;

import com.kynsof.patients.domain.dto.PatientInsuranceDto;
import com.kynsof.patients.infrastructure.entity.Allergy;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IPatientInsuranceService {
    UUID create(PatientInsuranceDto patients);

    UUID update(PatientInsuranceDto patients);

    void delete(UUID id);

    PatientInsuranceDto findById(UUID id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}