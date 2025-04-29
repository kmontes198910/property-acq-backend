package com.kynsoft.cirugia.domain.interfaces;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.cirugia.domain.model.RecoveryBed;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRecoveryBedService {
    Optional<RecoveryBed> findById(UUID id);
    List<RecoveryBed> findByBusinessId(UUID businessId);
    List<RecoveryBed> findAvailableBeds(UUID businessId);
    List<RecoveryBed> findByStatus(String status);
    List<RecoveryBed> findByType(String type);
    RecoveryBed create(RecoveryBed recoveryBed);
    RecoveryBed update(RecoveryBed recoveryBed);
    void updateStatus(UUID bedId, String status);
    void delete(UUID id);
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}