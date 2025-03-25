package com.kynsof.treatments.domain.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.treatments.domain.dto.PatientVaccineDto;
import com.kynsof.treatments.infrastructure.entity.PatientVaccine;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;


public interface IPatientVaccineService {
    UUID create(PatientVaccineDto entity);

    UUID update(PatientVaccine entity);

    void delete(UUID id);

    PatientVaccineDto findById(UUID id);

 //   PaginatedResponse findAll(Pageable pageable, UUID patientId);
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}