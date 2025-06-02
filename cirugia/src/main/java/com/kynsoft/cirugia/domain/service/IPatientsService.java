package com.kynsoft.cirugia.domain.service;

import com.kynsoft.cirugia.domain.dto.PatientDto;
import java.util.UUID;

public interface IPatientsService {

    UUID create(PatientDto patients);

    UUID update(PatientDto patients);

    void delete(UUID id);

    PatientDto findById(UUID id);
}
