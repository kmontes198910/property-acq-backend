package com.kynsof.hospitalizationService.infrastructure.service.impl;

import com.kynsof.hospitalizationService.application.response.BedResponse;
import com.kynsof.hospitalizationService.application.response.EmergencyCaseAndBedResponse;
import com.kynsof.hospitalizationService.application.response.EmergencyCaseResponse;
import com.kynsof.hospitalizationService.domain.dto.EmergencyCaseDto;
import com.kynsof.hospitalizationService.domain.dto.EmergencyCaseUpdateDto;
import com.kynsof.hospitalizationService.domain.dto.command.CreateEmergencyCaseDto;
import com.kynsof.hospitalizationService.domain.dto.enun.BedStatus;
import com.kynsof.hospitalizationService.domain.dto.exception.BedNotFoundException;
import com.kynsof.hospitalizationService.domain.dto.exception.BedOccupiedNotFoundException;
import com.kynsof.hospitalizationService.domain.dto.exception.EmergencyCaseNotFoundException;
import com.kynsof.hospitalizationService.domain.service.IEmergencyCaseService;
import com.kynsof.hospitalizationService.infrastructure.entity.Bed;
import com.kynsof.hospitalizationService.infrastructure.entity.EmergencyCase;
import com.kynsof.hospitalizationService.infrastructure.entity.EmergencyCaseBed;
import com.kynsof.hospitalizationService.infrastructure.entity.Patients;
import com.kynsof.hospitalizationService.infrastructure.repositories.command.BedWriteDataJPARepository;
import com.kynsof.hospitalizationService.infrastructure.repositories.command.EmergencyCaseBedWriteDataJPARepository;
import com.kynsof.hospitalizationService.infrastructure.repositories.command.EmergencyCaseWriteDataJPARepository;
import com.kynsof.hospitalizationService.infrastructure.repositories.query.BedReadDataJPARepository;
import com.kynsof.hospitalizationService.infrastructure.repositories.query.EmergencyCaseReadDataJPARepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmergencyCaseServiceImpl implements IEmergencyCaseService {

    private final EmergencyCaseWriteDataJPARepository repositoryCommand;
    private final EmergencyCaseReadDataJPARepository repositoryQuery;
    private final BedReadDataJPARepository bedReadDataJPARepository;
    private final BedWriteDataJPARepository bedWriteDataJPARepository;
    private final EmergencyCaseBedWriteDataJPARepository emergencyCaseBedWriteDataJPARepository;

    public EmergencyCaseServiceImpl(EmergencyCaseWriteDataJPARepository repositoryCommand,
            EmergencyCaseReadDataJPARepository repositoryQuery,
            BedReadDataJPARepository bedReadDataJPARepository,
            EmergencyCaseBedWriteDataJPARepository emergencyCaseBedWriteDataJPARepository,
            BedWriteDataJPARepository bedWriteDataJPARepository) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
        this.bedReadDataJPARepository = bedReadDataJPARepository;
        this.bedWriteDataJPARepository = bedWriteDataJPARepository;
        this.emergencyCaseBedWriteDataJPARepository = emergencyCaseBedWriteDataJPARepository;
    }

    @Override
    @Transactional
    public UUID create(EmergencyCaseDto dto) {
        EmergencyCase object = new EmergencyCase(dto);
        this.repositoryCommand.save(object);
        return dto.getId();
    }

    @Override
    @Transactional
    public void createWithBed(CreateEmergencyCaseDto dto) {
        EmergencyCase emergencyCase = new EmergencyCase();
        emergencyCase.setId(dto.getId());
        emergencyCase.setPatient(new Patients(dto.getPatient())); // Asume que Patient existe
        emergencyCase.setAdmissionDate(dto.getAdmissionDate());
        emergencyCase.setAdmissionTime(dto.getAdmissionTime());
        emergencyCase.setAdmissionType(dto.getAdmissionType());
        emergencyCase.setStatus(dto.getStatus());

        emergencyCase = repositoryCommand.save(emergencyCase); // Guarda primero el caso

        Bed bed = bedReadDataJPARepository.findById(dto.getBed()).orElseThrow(() -> new BedNotFoundException(dto.getBed()));
        if (bed.getStatus().equals(BedStatus.OCCUPIED)) {
            throw new BedOccupiedNotFoundException(dto.getBed());
        }
        bed.setStatus(BedStatus.OCCUPIED);
        bedWriteDataJPARepository.save(bed);

        EmergencyCaseBed assignment = new EmergencyCaseBed();
        assignment.setId(UUID.randomUUID());
        assignment.setEmergencyCase(emergencyCase);
        assignment.setBed(bed);
        emergencyCaseBedWriteDataJPARepository.save(assignment);
    }

    @Override
    @Transactional
    public UUID update(EmergencyCaseDto dto) {
        if (dto == null || dto.getId() == null) {
            throw new IllegalArgumentException("Emergency Case or ID cannot be null");
        }

        EmergencyCase update = new EmergencyCase(dto);
        this.repositoryCommand.save(update);
        return dto.getId();
    }

    @Override
    public EmergencyCaseDto findById(UUID id) {
        Optional<EmergencyCase> contactInformation = this.repositoryQuery.findById(id);
        if (contactInformation.isPresent()) {
            return contactInformation.get().toAggregate();
        }
        throw new EmergencyCaseNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<EmergencyCase> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<EmergencyCase> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<EmergencyCase> data) {
        List<EmergencyCaseResponse> patients = new ArrayList<>();
        for (EmergencyCase p : data.getContent()) {
            patients.add(new EmergencyCaseResponse(p.toAggregate()));
        }
        return new PaginatedResponse(patients, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        try {
            this.repositoryCommand.deleteById(id);
        } catch (Exception e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.NOT_DELETE, new ErrorField("id", "Element cannot be deleted has a related element.")));
        }
    }

    @Override
    public void simpleUpdate(EmergencyCaseUpdateDto dto) {

        Optional<EmergencyCase> update = this.repositoryQuery.findByIdForUpdate(dto.getId());
        if (update.isPresent()) {
            EmergencyCase emergencyCase = update.get();
            emergencyCase.setAdmissionDate(dto.getAdmissionDate());
            emergencyCase.setAdmissionTime(dto.getAdmissionTime());
            emergencyCase.setAdmissionType(dto.getAdmissionType());
            emergencyCase.setStatus(dto.getStatus());

            this.repositoryCommand.save(emergencyCase);
        } else {
            throw new EmergencyCaseNotFoundException(dto.getId());
        }

    }

    @Override
    public EmergencyCaseAndBedResponse getBedByEmergencyCaseId(UUID emergencyCaseId) {
        EmergencyCase emergencyCase = repositoryQuery.findWithBedsById(emergencyCaseId).orElseThrow(() -> new EmergencyCaseNotFoundException(emergencyCaseId));

        List<BedResponse> beds = new ArrayList<>();
        for (EmergencyCaseBed bed : emergencyCase.getBeds()) {
            beds.add(new BedResponse(
                    bed.getBed().getId(), 
                    bed.getBed().getCode(), 
                    bed.getBed().getName(), 
                    bed.getBed().getUbication().toAggregate(), 
                    bed.getBed().getStatus())
            );
        }
        return new EmergencyCaseAndBedResponse(new EmergencyCaseDto(
                emergencyCase.getId(), 
                emergencyCase.getPatient().toAggregate(), 
                emergencyCase.getAdmissionDate(), 
                emergencyCase.getAdmissionTime(), 
                emergencyCase.getAdmissionType(), 
                emergencyCase.getStatus()
                ), 
                beds
        );
    }

}
