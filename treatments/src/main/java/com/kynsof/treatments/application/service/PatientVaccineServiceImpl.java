package com.kynsof.treatments.application.service;

import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsof.treatments.application.query.patientVaccine.getall.PatientVaccineResponse;
import com.kynsof.treatments.domain.dto.PatientVaccineDto;
import com.kynsof.treatments.domain.dto.enumDto.VaccinationStatus;
import com.kynsof.treatments.domain.service.IPatientVaccineService;
import com.kynsof.treatments.infrastructure.entity.PatientVaccine;
import com.kynsof.treatments.infrastructure.repositories.command.PatientVaccineWriteDataJPARepository;
import com.kynsof.treatments.infrastructure.repositories.query.PatientVaccineReadDataJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PatientVaccineServiceImpl implements IPatientVaccineService {

    @Autowired
    private PatientVaccineWriteDataJPARepository repositoryCommand;

    @Autowired
    private PatientVaccineReadDataJPARepository repositoryQuery;

    @Override
    public UUID create(PatientVaccineDto dto) {
        PatientVaccine entity = this.repositoryCommand.save(new PatientVaccine(dto));
        return entity.getId();
    }

    @Override
    public UUID update(PatientVaccine dto) {
        if (dto == null || dto.getId() == null) {
            throw new IllegalArgumentException("Patient DTO or ID cannot be null");
        }
        dto.setUpdatedAt(LocalDateTime.now());
        PatientVaccine entity = this.repositoryCommand.save(dto);
        return entity.getId();
    }

    @Override
    public PatientVaccineDto findById(UUID id) {
        Optional<PatientVaccine> patientVaccine = this.repositoryQuery.findById(id);
        if (patientVaccine.isPresent()) {
            return patientVaccine.get().toAggregate();
        }
        throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.PATIENT_VACCINE_NOT_FOUND, new ErrorField("id", "Relationship not found.")));
    }

//    @Override
//    public PaginatedResponse findAll(Pageable pageable, UUID patientId) {
//        PatientVaccineSpecifications specifications = new PatientVaccineSpecifications(patientId);
//        Page<PatientVaccine> data = this.repositoryQuery.findAll(specifications, pageable);
//
//        return getPaginatedResponse(data);
//    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        for (FilterCriteria filter : filterCriteria) {
            if ("status".equals(filter.getKey()) && filter.getValue() instanceof String) {
                try {
                    VaccinationStatus enumValue = VaccinationStatus.valueOf((String) filter.getValue());
                    filter.setValue(enumValue);
                } catch (IllegalArgumentException e) {
                    System.err.println("Valor inválido para el tipo Enum RoleStatus: " + filter.getValue());
                }
            }
        }
        GenericSpecificationsBuilder<PatientVaccine> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<PatientVaccine> data = this.repositoryQuery.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<PatientVaccine> data) {
        List<PatientVaccineResponse> patientVaccineResponses = new ArrayList<>();
        for (PatientVaccine p : data.getContent()) {
            patientVaccineResponses.add(new PatientVaccineResponse(p.toAggregate()));
        }
        return new PaginatedResponse(patientVaccineResponses, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
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
