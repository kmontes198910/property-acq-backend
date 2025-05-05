package com.kynsoft.cirugia.domain.service;

import com.kynsoft.cirugia.domain.dto.Treatment;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ITreatmentRepository {
    Treatment create(Treatment treatment);
    Optional<Treatment> findById(String id);
    List<Treatment> findBySurgeryId(UUID surgeryId);
    void deleteById(String id);
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}