package com.kynsof.patients.infrastructure.services;


import com.kynsof.patients.application.query.allergy.getall.AllergyResponse;
import com.kynsof.patients.application.query.patientsInsurance.getById.PatientsInsuranceByIdResponse;
import com.kynsof.patients.domain.dto.PatientInsuranceDto;
import com.kynsof.patients.domain.dto.enumTye.Status;
import com.kynsof.patients.domain.service.IAllergyService;
import com.kynsof.patients.domain.service.IPatientInsuranceService;
import com.kynsof.patients.infrastructure.entity.Allergy;
import com.kynsof.patients.infrastructure.entity.Insurance;
import com.kynsof.patients.infrastructure.entity.PatientInsurance;
import com.kynsof.patients.infrastructure.entity.Patients;
import com.kynsof.patients.infrastructure.repository.command.PatientInsuranceWriteDataJPARepository;
import com.kynsof.patients.infrastructure.repository.query.InsuranceReadDataJPARepository;
import com.kynsof.patients.infrastructure.repository.query.PatientInsuranceReadDataJPARepository;
import com.kynsof.patients.infrastructure.repository.query.PatientsReadDataJPARepository;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PatientInsuranceServiceImpl implements IPatientInsuranceService {

    private final PatientInsuranceWriteDataJPARepository repositoryCommand;

    private final PatientInsuranceReadDataJPARepository repositoryQuery;
    private final PatientsReadDataJPARepository patientsReadDataJPARepository;
    private final InsuranceReadDataJPARepository insuranceReadDataJPARepository;

    public PatientInsuranceServiceImpl(PatientInsuranceWriteDataJPARepository repositoryCommand, PatientInsuranceReadDataJPARepository repositoryQuery, PatientsReadDataJPARepository patientsReadDataJPARepository, InsuranceReadDataJPARepository insuranceReadDataJPARepository) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
        this.patientsReadDataJPARepository = patientsReadDataJPARepository;
        this.insuranceReadDataJPARepository = insuranceReadDataJPARepository;
    }

    @Override
    public UUID create(PatientInsuranceDto dto) {
        Patients patients = patientsReadDataJPARepository.findById(dto.getPatientId()).orElseThrow();
        Insurance insurance = insuranceReadDataJPARepository.findById(dto.getInsuranceId()).orElseThrow();
        PatientInsurance allergy =this.repositoryCommand.save(new PatientInsurance(patients, insurance));
        return allergy.getId();
    }

    @Override
    public UUID update(PatientInsuranceDto dto) {
        if (dto == null || dto.getId() == null) {
            throw new IllegalArgumentException("Patient DTO or ID cannot be null");
        }
        PatientInsurance patientInsurance = this.repositoryCommand.findById(dto.getPatientId()).orElseThrow();
        Insurance insurance = insuranceReadDataJPARepository.findById(dto.getInsuranceId()).orElseThrow();
        patientInsurance.setStatus(dto.getStatus());
        patientInsurance.setInsurance(insurance);
        PatientInsurance entity = this.repositoryCommand.save(patientInsurance);
        return entity.getId();
    }


    @Override
    public PatientInsuranceDto findById(UUID id) {
        Optional<PatientInsurance> contactInformation = this.repositoryQuery.findById(id);
        if (contactInformation.isPresent()) {
            return contactInformation.get().toAggregate();
        }
       // throw new BusinessException(DomainErrorMessage.BUSINESS_NOT_FOUND, "Contact Information not found.");
        throw new RuntimeException("Patients not found.");
    }


    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Allergy> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<PatientInsurance> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<PatientInsurance> data) {
        List<PatientsInsuranceByIdResponse> patientsInsuranceByIdResponses = new ArrayList<>();
        for (PatientInsurance p : data.getContent()) {
            patientsInsuranceByIdResponses.add(new PatientsInsuranceByIdResponse(p.toAggregate()));
        }
        return new PaginatedResponse(patientsInsuranceByIdResponses, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public void delete(UUID id) {
        try {
           PatientInsurance patientInsurance = this.repositoryCommand.findById(id).orElseThrow();
           patientInsurance.setStatus(Status.INACTIVE);
           repositoryCommand.save(patientInsurance);
        } catch (Exception e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.NOT_DELETE, new ErrorField("id", "Element cannot be deleted has a related element.")));
        }
    }

}
