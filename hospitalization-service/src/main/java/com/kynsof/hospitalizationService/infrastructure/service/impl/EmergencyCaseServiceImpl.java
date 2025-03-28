package com.kynsof.hospitalizationService.infrastructure.service.impl;

import com.kynsof.hospitalizationService.application.response.EmergencyCaseResponse;
import com.kynsof.hospitalizationService.domain.dto.EmergencyCaseDto;
import com.kynsof.hospitalizationService.domain.service.IEmergencyCaseService;
import com.kynsof.hospitalizationService.infrastructure.entity.EmergencyCase;
import com.kynsof.hospitalizationService.infrastructure.repositories.command.EmergencyCaseWriteDataJPARepository;
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

@Service
public class EmergencyCaseServiceImpl implements IEmergencyCaseService {

    private final EmergencyCaseWriteDataJPARepository repositoryCommand;
    private final EmergencyCaseReadDataJPARepository repositoryQuery;

    public EmergencyCaseServiceImpl(EmergencyCaseWriteDataJPARepository repositoryCommand, EmergencyCaseReadDataJPARepository repositoryQuery) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
    }

    @Override
    public UUID create(EmergencyCaseDto dto) {
        EmergencyCase object = new EmergencyCase(dto);
        this.repositoryCommand.save(object);
        return dto.getId();
    }

    @Override
    public UUID update(EmergencyCaseDto dto) {
        if (dto == null || dto.getId() == null) {
            throw new IllegalArgumentException("Patient DTO or ID cannot be null");
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
        throw new RuntimeException("ContactInfo not found.");
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
    public void delete(UUID id) {
        try {
            this.repositoryCommand.deleteById(id);
        } catch (Exception e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.NOT_DELETE, new ErrorField("id", "Element cannot be deleted has a related element.")));
        }
    }

}
