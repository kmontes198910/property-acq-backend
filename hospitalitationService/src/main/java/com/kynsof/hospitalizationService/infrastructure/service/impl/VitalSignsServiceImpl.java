package com.kynsof.hospitalizationService.infrastructure.service.impl;

import com.kynsof.hospitalizationService.application.response.VitalSignsResponse;
import com.kynsof.hospitalizationService.domain.dto.VitalSignsDto;
import com.kynsof.hospitalizationService.domain.dto.exception.EmergencyCaseNotFoundException;
import com.kynsof.hospitalizationService.domain.dto.exception.VitalSignsNotFoundException;
import com.kynsof.hospitalizationService.domain.service.IVitalSignsService;
import com.kynsof.hospitalizationService.infrastructure.entity.VitalSigns;
import com.kynsof.hospitalizationService.infrastructure.repositories.command.VitalSignsWriteDataJPARepository;
import com.kynsof.hospitalizationService.infrastructure.repositories.query.VitalSignsReadDataJPARepository;
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
public class VitalSignsServiceImpl implements IVitalSignsService {

    private final VitalSignsWriteDataJPARepository repositoryCommand;
    private final VitalSignsReadDataJPARepository repositoryQuery;

    public VitalSignsServiceImpl(VitalSignsWriteDataJPARepository repositoryCommand, VitalSignsReadDataJPARepository repositoryQuery) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
    }

    @Override
    @Transactional
    public UUID create(VitalSignsDto dto) {
        VitalSigns object = new VitalSigns(dto);
        this.repositoryCommand.save(object);
        return dto.getId();
    }

    @Override
    @Transactional
    public UUID update(VitalSignsDto dto) {
        if (dto == null || dto.getId() == null) {
            throw new IllegalArgumentException("Emergency Case or ID cannot be null");
        }

        VitalSigns update = new VitalSigns(dto);
        this.repositoryCommand.save(update);
        return dto.getId();
    }

    @Override
    public VitalSignsDto findById(UUID id) {
        Optional<VitalSigns> contactInformation = this.repositoryQuery.findById(id);
        if (contactInformation.isPresent()) {
            return contactInformation.get().toAggregate();
        }
        throw new VitalSignsNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<VitalSigns> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<VitalSigns> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<VitalSigns> data) {
        List<VitalSignsResponse> patients = new ArrayList<>();
        for (VitalSigns p : data.getContent()) {
            patients.add(new VitalSignsResponse(p.toAggregate()));
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

}
