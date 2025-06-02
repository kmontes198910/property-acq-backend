package com.kynsoft.medicaltest.domain.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.medicaltest.domain.dto.LabTestRequestDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ILabTestRequestService {

    void create(LabTestRequestDto labTest);
    void update(LabTestRequestDto labTest);
    void delete(UUID id);
    LabTestRequestDto findById(UUID id);
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}
