package com.kynsoft.cirugia.domain.service;

import com.kynsoft.cirugia.domain.dto.PreOperative;

import java.util.Optional;
import java.util.UUID;

public interface IPreOperativeRepository {
    PreOperative save(PreOperative preOperative);
    Optional<PreOperative> findById(String id);
    Optional<PreOperative> findBySurgeryId(UUID surgeryId);
    void deleteById(String id);
}