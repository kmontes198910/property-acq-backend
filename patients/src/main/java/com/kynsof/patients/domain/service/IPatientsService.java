package com.kynsof.patients.domain.service;

import com.kynsof.patients.domain.dto.DependentPatientDto;
import com.kynsof.patients.domain.dto.PatientByIdDto;
import com.kynsof.patients.domain.dto.PatientDto;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IPatientsService {
    UUID create(PatientDto patients);
    UUID createDependent(DependentPatientDto patients);
    UUID update(PatientDto patients);
    void updateDependent(DependentPatientDto patients);
    void delete(PatientDto patientDto);
    void deleteSystem(PatientDto patientDto);

    PatientByIdDto findById(UUID id);
    PatientByIdDto findByKeyCloakId(UUID id);
    PatientDto findByIdSimple(UUID id);
    PaginatedResponse findAll(Pageable pageable);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);

    void createInsurance(UUID patientId, List<UUID> insuranceIds);

    PatientDto findByIdentification(String identification);

    Long countByIdentificationAndNotId(String identification, UUID id);
    Long countPatient();

    void updateKeyCloak(UUID patientId, UUID keyCloakId);
}