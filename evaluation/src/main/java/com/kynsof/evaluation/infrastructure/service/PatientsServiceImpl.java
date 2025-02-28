package com.kynsof.evaluation.infrastructure.service;

import com.kynsof.evaluation.application.object.response.PatientResponse;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.evaluation.domain.dto.PatientDto;
import com.kynsof.evaluation.domain.service.IPatientsService;
import com.kynsof.evaluation.infrastructure.entity.Patients;
import com.kynsof.evaluation.infrastructure.repositories.command.PatientsWriteDataJPARepository;
import com.kynsof.evaluation.infrastructure.repositories.query.PatientsReadDataJPARepository;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class PatientsServiceImpl implements IPatientsService {

    private final PatientsWriteDataJPARepository repositoryCommand;
    private final PatientsReadDataJPARepository repositoryQuery;

    public PatientsServiceImpl(PatientsWriteDataJPARepository repositoryCommand,
            PatientsReadDataJPARepository repositoryQuery) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
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
        return repositoryQuery.findById(id)
                .map(Patients::toAggregate)
                .orElseThrow(() -> new BusinessNotFoundException(new GlobalBusinessException(
                DomainErrorMessage.PATIENTS_NOT_FOUND,
                new ErrorField("id", "Patient not found."))));
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

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Patients> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Patients> data = this.repositoryQuery.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<Patients> data) {
        List<PatientResponse> servicesResponses = new ArrayList<>();
        for (Patients s : data.getContent()) {
            servicesResponses.add(new PatientResponse(s.toAggregate()));
        }
        return new PaginatedResponse(servicesResponses, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

}
