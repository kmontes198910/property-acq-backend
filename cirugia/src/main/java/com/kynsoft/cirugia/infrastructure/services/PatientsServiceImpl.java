package com.kynsoft.cirugia.infrastructure.services;

import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsoft.cirugia.domain.dto.PatientDto;
import com.kynsoft.cirugia.domain.dto.exception.PatientNotFoundException;
import com.kynsoft.cirugia.domain.service.IPatientsService;
import com.kynsoft.cirugia.infrastructure.entities.Patient;
import com.kynsoft.cirugia.infrastructure.repository.command.PatientsWriteDataJPARepository;
import com.kynsoft.cirugia.infrastructure.repository.query.PatientsReadDataJPARepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PatientsServiceImpl implements IPatientsService {

    private final PatientsWriteDataJPARepository repositoryCommand;

    private final PatientsReadDataJPARepository repositoryQuery;

    public PatientsServiceImpl(PatientsWriteDataJPARepository repositoryCommand, PatientsReadDataJPARepository repositoryQuery) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
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
        Optional<Patient> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get().toAggregate();
        }
        throw new PatientNotFoundException(id);
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
