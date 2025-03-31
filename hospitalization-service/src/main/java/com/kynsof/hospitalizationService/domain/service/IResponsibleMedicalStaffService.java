package com.kynsof.hospitalizationService.domain.service;

import com.kynsof.hospitalizationService.domain.dto.ResponsibleMedicalStaffDto;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IResponsibleMedicalStaffService {
    UUID create(ResponsibleMedicalStaffDto object);

    UUID update(ResponsibleMedicalStaffDto object);

    void delete(UUID id);

    ResponsibleMedicalStaffDto findById(UUID id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}