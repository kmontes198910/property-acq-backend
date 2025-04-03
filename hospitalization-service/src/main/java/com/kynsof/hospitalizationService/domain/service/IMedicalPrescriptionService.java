package com.kynsof.hospitalizationService.domain.service;

import com.kynsof.hospitalizationService.domain.dto.MedicalPrescriptionDto;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IMedicalPrescriptionService {
    UUID create(MedicalPrescriptionDto object);

    UUID update(MedicalPrescriptionDto object);

    void delete(UUID id);

    MedicalPrescriptionDto findById(UUID id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}