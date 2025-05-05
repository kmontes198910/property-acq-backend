package com.kynsoft.cirugia.domain.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.cirugia.domain.dto.VitalSigns;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IVitalSignsService {
    Optional<VitalSigns> findById(UUID id);
    List<VitalSigns> findByPatientId(UUID patientId);
    List<VitalSigns> findBySurgeryId(UUID surgeryId);
    List<VitalSigns> findLatestByPatientId(UUID patientId);
    List<VitalSigns> findLatestBySurgeryId(UUID surgeryId);
    VitalSigns create(VitalSigns vitalSigns);
    VitalSigns update(VitalSigns vitalSigns);
    void delete(UUID id);
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}