package com.kynsof.patients.infrastructure.services;

import com.kynsof.patients.application.query.patients.getall.PatientsResponse;
import com.kynsof.patients.domain.dto.DependentPatientDto;
import com.kynsof.patients.domain.dto.PatientByIdDto;
import com.kynsof.patients.domain.dto.PatientDto;
import com.kynsof.patients.domain.dto.enumTye.Status;
import com.kynsof.patients.domain.service.IPatientsService;
import com.kynsof.patients.infrastructure.entity.Insurance;
import com.kynsof.patients.infrastructure.entity.PatientInsurance;
import com.kynsof.patients.infrastructure.entity.Patients;
import com.kynsof.patients.infrastructure.repository.command.PatientInsuranceWriteDataJPARepository;
import com.kynsof.patients.infrastructure.repository.command.PatientsWriteDataJPARepository;
import com.kynsof.patients.infrastructure.repository.query.InsuranceReadDataJPARepository;
import com.kynsof.patients.infrastructure.repository.query.PatientsReadDataJPARepository;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PatientsServiceImpl implements IPatientsService {

    private final PatientsWriteDataJPARepository repositoryCommand;

    private final PatientsReadDataJPARepository repositoryQuery;

    private final InsuranceReadDataJPARepository insuranceReadDataJPARepository;
    private final PatientInsuranceWriteDataJPARepository patientInsuranceWriteDataJPARepository;

    public PatientsServiceImpl(PatientsReadDataJPARepository repositoryQuery, PatientsWriteDataJPARepository repositoryCommand, InsuranceReadDataJPARepository insuranceReadDataJPARepository, PatientInsuranceWriteDataJPARepository patientInsuranceWriteDataJPARepository) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
        this.insuranceReadDataJPARepository = insuranceReadDataJPARepository;
        this.patientInsuranceWriteDataJPARepository = patientInsuranceWriteDataJPARepository;
    }

    @Override
    public UUID create(PatientDto patients) {
        Patients obj = new Patients(patients);
        obj.setKeycloakId(patients.getId());
        Patients entity = this.repositoryCommand.save(obj);
        return entity.getId();
    }

    @Override
    public UUID createDependent(DependentPatientDto patients) {
        Patients entity = this.repositoryCommand.save(new Patients(patients));
        return entity.getId();
    }

    @Override
    public UUID update(PatientDto patientDto) {
        if (patientDto == null || patientDto.getId() == null) {
            throw new IllegalArgumentException("Patient DTO or ID cannot be null");
        }

        Patients existingPatient = repositoryQuery.findById(patientDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + patientDto.getId()));
        existingPatient.setFirstName(patientDto.getName());
        existingPatient.setLastName(patientDto.getLastName());
        existingPatient.setIdentification(patientDto.getIdentification());
        existingPatient.setGender(patientDto.getGender());
        existingPatient.setPhoto(patientDto.getPhoto());
        existingPatient.setHasDisability(patientDto.getHasDisability());
        existingPatient.setIsPregnant(patientDto.getIsPregnant());
        existingPatient.setDisabilityType(patientDto.getDisabilityType());
        existingPatient.setUpdatedAt(LocalDateTime.now());
        existingPatient.setProfession(patientDto.getProfession());

        // Persistir los cambios
        repositoryCommand.save(existingPatient);

        return existingPatient.getId();
    }

    @Override
    public void updateDependent(DependentPatientDto dependentPatientDto) {
        if (dependentPatientDto == null || dependentPatientDto.getId() == null) {
            throw new IllegalArgumentException("DependentPatient DTO or ID cannot be null");
        }

        this.repositoryQuery.findById(dependentPatientDto.getId())
                .map(dependentPatient -> {
                    if (dependentPatientDto.getName() != null) {
                        dependentPatient.setFirstName(dependentPatientDto.getName());
                    }
                    if (dependentPatientDto.getLastName() != null) {
                        dependentPatient.setLastName(dependentPatientDto.getLastName());
                    }
                    if (dependentPatientDto.getIdentification() != null) {
                        dependentPatient.setIdentification(dependentPatientDto.getIdentification());
                    }
                    if (dependentPatientDto.getGender() != null) {
                        dependentPatient.setGender(dependentPatientDto.getGender());
                    }
                    if (dependentPatientDto.getStatus() != null) {
                        dependentPatient.setStatus(dependentPatientDto.getStatus());
                    }
                    if (dependentPatientDto.getHasDisability() != null) {
                        dependentPatient.setHasDisability(dependentPatientDto.getHasDisability());
                    }
                    if (dependentPatientDto.getIsPregnant() != null) {
                        dependentPatient.setIsPregnant(dependentPatientDto.getIsPregnant());
                    }
                    if (dependentPatientDto.getFamilyRelationship() != null) {
                        dependentPatient.setFamilyRelationship(dependentPatientDto.getFamilyRelationship());
                    }

                    if (dependentPatientDto.getPhoto() != null) {
                        dependentPatient.setPhoto(dependentPatientDto.getPhoto());
                    }
                    // Considera cómo actualizar la información del paciente principal (prime) si es necesario.
                    // Esto puede implicar buscar el paciente principal por su ID y establecer la relación adecuadamente.

                    return this.repositoryCommand.save(dependentPatient);
                })
                .orElseThrow(() -> new EntityNotFoundException("Dependent patient with ID " + dependentPatientDto.getId() + " not found"));

    }

    @Override
    public PatientByIdDto findById(UUID id) {
        Optional<Patients> patient = this.repositoryQuery.findById(id);
        if (patient.isPresent()) {
            return patient.get().toAggregateById();
        }
        throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.PATIENTS_NOT_FOUND, new ErrorField("id", "Patient not found.")));

    }

    @Override
    public PatientByIdDto findByKeyCloakId(UUID id) {
        Optional<Patients> patient = this.repositoryQuery.findByKeyCloakId(id);
        if (patient.isPresent()) {
            return patient.get().toAggregateById();
        }
        throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.PATIENTS_NOT_FOUND, new ErrorField("id", "Patient not found.")));

    }

    //    @Cacheable(cacheNames = CacheConfig.USER_CACHE, unless = "#result == null")
    @Override
    public PatientDto findByIdSimple(UUID id) {
        Optional<Patients> patient = this.repositoryQuery.findById(id);
        if (patient.isPresent()) {
            return patient.get().toAggregate();
        }
        throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.PATIENTS_NOT_FOUND, new ErrorField("id", "Patient not found.")));

    }

    @Override
    public PaginatedResponse findAll(Pageable pageable) {
        Page<Patients> data = this.repositoryQuery.findAll(pageable);
        return getPaginatedResponse(data);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Patients> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Patients> data = this.repositoryQuery.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<Patients> data) {
        List<PatientsResponse> patients = new ArrayList<>();
        for (Patients p : data.getContent()) {
            patients.add(new PatientsResponse(p.toAggregate()));
        }
        return new PaginatedResponse(patients, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Transactional
    @Override
    public void createInsurance(UUID patientId, List<UUID> insuranceIds) {
        Optional<Patients> patientOpt = repositoryQuery.findById(patientId);
        if (patientOpt.isEmpty()) {
            throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.PATIENTS_NOT_FOUND, new ErrorField("id", "Patient not found.")));
        }
        Patients patient = patientOpt.get();

        List<Insurance> insurances = insuranceReadDataJPARepository.findAllById(insuranceIds);
        if (insurances.size() != insuranceIds.size()) {
            throw new RuntimeException("Una o más aseguradoras no encontradas");
        }

        insurances.forEach(insurance -> {
            PatientInsurance patientInsurance = new PatientInsurance();
            patientInsurance.setPatient(patient);
            patientInsurance.setInsurance(insurance);
            patientInsurance.setStatus(Status.ACTIVE);
            patientInsuranceWriteDataJPARepository.save(patientInsurance);
        });
    }

    @Override
    public PatientDto findByIdentification(String identification) {
        Optional<Patients> patient = this.repositoryQuery.findByIdentification(identification);
        if (patient.isPresent()) {
            return patient.get().toAggregate();
        }
        throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.PATIENTS_NOT_FOUND, new ErrorField("id", "Patient not found.")));
    }

    @Override
    public void delete(PatientDto patientDto) {
        try {
            this.repositoryCommand.deleteById(patientDto.getId());
        } catch (Exception e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.NOT_DELETE, new ErrorField("id", "Element cannot be deleted has a related element.")));
        }
    }

    @Override
    public Long countByIdentificationAndNotId(String identification, UUID id) {
        return this.repositoryQuery.countByIdentificationAndNotId(identification, id);
    }

    @Override
    public Long countPatient() {
        return this.repositoryQuery.countPatient();
    }

    @Override
    public void updateKeyCloak(UUID patientId, UUID keyCloakId) {
        Patients patients = this.repositoryQuery.findById(patientId).get();
        patients.setKeycloakId(keyCloakId);
        this.repositoryCommand.save(patients);
    }

}
