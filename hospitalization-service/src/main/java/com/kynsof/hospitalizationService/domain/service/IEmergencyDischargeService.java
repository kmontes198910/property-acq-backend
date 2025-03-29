package com.kynsof.hospitalizationService.domain.service;

import com.kynsof.hospitalizationService.domain.dto.EmergencyDischargeDto;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IEmergencyDischargeService {
    UUID create(EmergencyDischargeDto object);

    UUID update(EmergencyDischargeDto object);

    void delete(UUID id);

    EmergencyDischargeDto findById(UUID id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}