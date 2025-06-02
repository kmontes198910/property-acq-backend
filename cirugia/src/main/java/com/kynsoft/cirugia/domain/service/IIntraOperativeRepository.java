package com.kynsoft.cirugia.domain.service;

import com.kynsoft.cirugia.domain.dto.IntraOperative;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IIntraOperativeRepository {
    IntraOperative create(IntraOperative intraOperative);
    Optional<IntraOperative> findById(String id);
    List<IntraOperative> findBySurgeryId(UUID surgeryId);
    void deleteById(String id);
    void update(IntraOperative intraOperative);
}