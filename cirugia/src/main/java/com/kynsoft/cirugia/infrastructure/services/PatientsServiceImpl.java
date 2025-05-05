package com.kynsoft.cirugia.infrastructure.services;

import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.http.entity.PatientHttp;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsoft.cirugia.domain.dto.PatientDto;
import com.kynsoft.cirugia.domain.dto.exception.PatientNotFoundException;
import com.kynsoft.cirugia.domain.service.IPatientsService;
import com.kynsoft.cirugia.infrastructure.entities.Patient;
import com.kynsoft.cirugia.infrastructure.repository.command.PatientsWriteDataJPARepository;
import com.kynsoft.cirugia.infrastructure.repository.query.PatientsReadDataJPARepository;
import com.kynsoft.cirugia.infrastructure.services.http.PatientHttpUUIDService;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PatientsServiceImpl implements IPatientsService {

    private final PatientsWriteDataJPARepository repositoryCommand;

    private final PatientsReadDataJPARepository repositoryQuery;
    private final PatientHttpUUIDService patientHttpUUIDService;

    public PatientsServiceImpl(PatientsWriteDataJPARepository repositoryCommand, PatientsReadDataJPARepository repositoryQuery, PatientHttpUUIDService patientHttpUUIDService) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
        this.patientHttpUUIDService = patientHttpUUIDService;
    }

    @Override
    public UUID create(PatientDto patients) {
        Patient entity = this.repositoryCommand.save(new Patient(patients));
        return entity.getId();
    }

    @Override
    public UUID update(PatientDto patientDto) {
        if (patientDto == null || patientDto.getId() == null) {
            throw new IllegalArgumentException("Patient DTO or ID cannot be null");
        }

        this.repositoryQuery.findById(patientDto.getId())
                .map(patient -> {
                    if (patientDto.getName() != null) patient.setName(patientDto.getName());
                    if (patientDto.getLastName() != null) patient.setLastName(patientDto.getLastName());
                    if (patientDto.getIdentification() != null)
                        patient.setIdentification(patientDto.getIdentification());
                    if(patientDto.getImage() != null) patient.setImage(patientDto.getImage());

                    return this.repositoryCommand.save(patient);
                })
                .orElseThrow(() -> new EntityNotFoundException("Patient with ID " + patientDto.getId() + " not found"));

        return patientDto.getId();
    }
    
    @Override
    public PatientDto findById(UUID id) {
        return repositoryQuery.findById(id)
                .map(Patient::toAggregate)
                .orElseGet(() -> { // Si no se encuentra, lo buscamos en el servicio externo
                    PatientHttp patient = patientHttpUUIDService.sendGetHttpRequest(id);

                    if (patient == null) {
                        throw new BusinessNotFoundException(new GlobalBusinessException(
                                DomainErrorMessage.PATIENTS_NOT_FOUND,
                                new ErrorField("id", "Patient not found in external service.")
                        ));
                    }

                    PatientDto patientDto = new PatientDto(
                            patient.getId(),
                            patient.getIdentification(),
                            patient.getEmail(),
                            patient.getName(),
                            patient.getLastName(),
                            "",
                            patient.getProfession()
                    );

                    // Guardamos en la base de datos
                    repositoryCommand.save(new Patient(patientDto));

                    return patientDto;
                });
    }

    @Override
    public void delete(UUID id) {
        try {
            this.repositoryCommand.deleteById(id);
        } catch (Exception e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.NOT_DELETE, new ErrorField("id", "Element cannot be deleted has a related element.")));
        }
    }

}
