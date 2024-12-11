package com.kynsof.treatments.domain.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.treatments.domain.dto.OptometryExamDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IOptometryExamService {
    OptometryExamDto findById(UUID id);
    List<OptometryExamDto> findAll();
    UUID create(OptometryExamDto examDto);
    void update(OptometryExamDto examDto);
    void delete(UUID id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filter);
    OptometryExamDto getLastCurrentExamByPatient(UUID patientId);
}