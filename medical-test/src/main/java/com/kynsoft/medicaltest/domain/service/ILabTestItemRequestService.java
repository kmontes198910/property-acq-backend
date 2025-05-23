package com.kynsoft.medicaltest.domain.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.medicaltest.domain.dto.LabTestItemRequestDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ILabTestItemRequestService {

    void create(LabTestItemRequestDto labTest);
    void update(LabTestItemRequestDto labTest);
    void delete(UUID id);
    LabTestItemRequestDto findById(UUID id);
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}
