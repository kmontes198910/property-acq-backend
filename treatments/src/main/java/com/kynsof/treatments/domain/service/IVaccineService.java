package com.kynsof.treatments.domain.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.treatments.domain.dto.VaccineDto;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface IVaccineService {

    VaccineDto findById(UUID id);
   //  PaginatedResponse findAll(Pageable pageable, String name, String description);
    PaginatedResponse getApplicableVaccines(LocalDate birthDate, UUID patientId, Pageable pageable);
    UUID create(VaccineDto vaccineDto);
    void update(VaccineDto vaccineDto);
    void delete(VaccineDto object);
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
    Long countByNameAndNotId(String name, UUID id);
}