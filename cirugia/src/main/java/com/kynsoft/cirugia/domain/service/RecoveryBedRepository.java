package com.kynsoft.cirugia.domain.service;

import com.kynsoft.cirugia.domain.dto.RecoveryBed;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RecoveryBedRepository {
    RecoveryBed save(RecoveryBed recoveryBed);
    Optional<RecoveryBed> findById(UUID id);
    List<RecoveryBed> findAll();
    List<RecoveryBed> findByStatus(String status);
    List<RecoveryBed> findByBusinessId(UUID businessId);
    List<RecoveryBed> findByType(String type);
    List<RecoveryBed> findAvailableBeds(UUID businessId);
    void delete(UUID id);
    void updateStatus(UUID bedId, String status);
}