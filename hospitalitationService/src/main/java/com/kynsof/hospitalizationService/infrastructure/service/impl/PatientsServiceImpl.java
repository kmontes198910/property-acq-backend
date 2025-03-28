package com.kynsof.hospitalizationService.infrastructure.service.impl;

import com.kynsof.hospitalizationService.domain.dto.PatientDto;
import com.kynsof.hospitalizationService.domain.service.IPatientsService;
import com.kynsof.hospitalizationService.infrastructure.entity.Patients;
import com.kynsof.hospitalizationService.infrastructure.repositories.command.PatientsWriteDataJPARepository;
import com.kynsof.hospitalizationService.infrastructure.repositories.query.PatientsReadDataJPARepository;
import com.kynsof.hospitalizationService.infrastructure.service.http.PatientHttpUUIDService;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.http.entity.PatientHttp;
import com.kynsof.share.core.domain.response.ErrorField;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Service
public class PatientsServiceImpl implements IPatientsService {

    private final PatientsWriteDataJPARepository repositoryCommand;
    private final PatientsReadDataJPARepository repositoryQuery;
    private final PatientHttpUUIDService patientHttpUUIDService;

    public PatientsServiceImpl(PatientsWriteDataJPARepository repositoryCommand,
                               PatientsReadDataJPARepository repositoryQuery, PatientHttpUUIDService patientHttpUUIDService) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
        this.patientHttpUUIDService = patientHttpUUIDService;
    }

    @Override
    @Transactional
    public UUID create(PatientDto patients) {
        Patients entity = repositoryCommand.save(new Patients(patients));
        return entity.getId();
    }

    @Override
    @Transactional
    public UUID update(PatientDto patientDto) {
        if (patientDto == null || patientDto.getId() == null) {
            throw new IllegalArgumentException("Patient DTO or ID cannot be null");
        }

        Patients updatedPatient = repositoryQuery.findById(patientDto.getId())
                .map(patient -> {
                    Optional.ofNullable(patientDto.getName()).ifPresent(patient::setName);
                    Optional.ofNullable(patientDto.getLastName()).ifPresent(patient::setLastName);
                    Optional.ofNullable(patientDto.getIdentification()).ifPresent(patient::setIdentification);
                    Optional.ofNullable(patientDto.getGender()).ifPresent(patient::setGender);
                    Optional.ofNullable(patientDto.getStatus()).ifPresent(patient::setStatus);
                    Optional.ofNullable(patientDto.getBirthDate()).ifPresent(patient::setBirthDate);
                    return repositoryCommand.save(patient);
                })
                .orElseThrow(() -> new EntityNotFoundException("Patient with ID " + patientDto.getId() + " not found"));

        return updatedPatient.getId();
    }

    @Override
    public PatientDto findById(UUID id) {
// Intentamos encontrar el paciente en la base de datos
        return repositoryQuery.findById(id)
                .map(Patients::toAggregate)
                .orElseGet(() -> { // Si no se encuentra, lo buscamos en el servicio externo
                    PatientHttp patient = patientHttpUUIDService.sendGetHttpRequest(id);

                    if (patient == null) {
                        throw new BusinessNotFoundException(new GlobalBusinessException(
                                DomainErrorMessage.PATIENTS_NOT_FOUND,
                                new ErrorField("id", "Patient not found in external service.")
                        ));
                    }

                    System.err.println("####################################");
                    System.err.println("####################################");
                    System.err.println("#################################### " + patient.getBirthDate());
                    System.err.println("####################################");
                    System.err.println("####################################");
                    LocalDate birthDate = LocalDate.parse(patient.getBirthDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                    PatientDto patientDto = new PatientDto(
                            patient.getId(),
                            patient.getIdentification(),
                            patient.getName(),
                            patient.getLastName(),
                            patient.getGender(),
                            "ACTIVE",
                            birthDate,
                            patient.getProfession()
                    );

                    // Guardamos en la base de datos
                    repositoryCommand.save(new Patients(patientDto));

                    return patientDto;
                });
    }

    @Override
    public void delete(UUID id) {
        try {
            repositoryCommand.deleteById(id);
        } catch (Exception e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(
                    DomainErrorMessage.NOT_DELETE,
                    new ErrorField("id", "Element cannot be deleted as it has a related element.")));
        }
    }
//
//    @Override
//    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
//        GenericSpecificationsBuilder<Patients> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
//        Page<Patients> data = this.repositoryQuery.findAll(specifications, pageable);
//        return getPaginatedResponse(data);
//    }
//
//    private PaginatedResponse getPaginatedResponse(Page<Patients> data) {
//        List<PatientResponse> servicesResponses = new ArrayList<>();
//        for (Patients s : data.getContent()) {
//            servicesResponses.add(new PatientResponse(s.toAggregate()));
//        }
//        return new PaginatedResponse(servicesResponses, data.getTotalPages(), data.getNumberOfElements(),
//                data.getTotalElements(), data.getSize(), data.getNumber());
//    }

}
