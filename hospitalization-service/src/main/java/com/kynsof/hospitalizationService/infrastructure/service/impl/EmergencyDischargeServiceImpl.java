package com.kynsof.hospitalizationService.infrastructure.service.impl;

import com.kynsof.hospitalizationService.application.response.EmergencyDischargeResponse;
import com.kynsof.hospitalizationService.domain.dto.EmergencyDischargeDto;
import com.kynsof.hospitalizationService.domain.dto.exception.EmergencyDischargeNotFoundException;
import com.kynsof.hospitalizationService.domain.service.IEmergencyDischargeService;
import com.kynsof.hospitalizationService.infrastructure.entity.EmergencyDischarge;
import com.kynsof.hospitalizationService.infrastructure.repositories.command.EmergencyDischargeWriteDataJPARepository;
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
import com.kynsof.hospitalizationService.infrastructure.repositories.query.EmergencyDischargeReadDataJPARepository;

@Service
public class EmergencyDischargeServiceImpl implements IEmergencyDischargeService {

    private final EmergencyDischargeWriteDataJPARepository repositoryCommand;
    private final EmergencyDischargeReadDataJPARepository repositoryQuery;

    public EmergencyDischargeServiceImpl(EmergencyDischargeWriteDataJPARepository repositoryCommand, EmergencyDischargeReadDataJPARepository repositoryQuery) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
    }

    @Override
    @Transactional
    public UUID create(EmergencyDischargeDto dto) {
        EmergencyDischarge object = new EmergencyDischarge(dto);
        this.repositoryCommand.save(object);
        return dto.getId();
    }

    @Override
    @Transactional
    public UUID update(EmergencyDischargeDto dto) {
        if (dto == null || dto.getId() == null) {
            throw new IllegalArgumentException("Emergency Case or ID cannot be null");
        }

        EmergencyDischarge update = new EmergencyDischarge(dto);
        this.repositoryCommand.save(update);
        return dto.getId();
    }

    @Override
    public EmergencyDischargeDto findById(UUID id) {
        Optional<EmergencyDischarge> object = this.repositoryQuery.findById(id);
        if (object.isPresent()) {
            return object.get().toAggregate();
        }
        throw new EmergencyDischargeNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<EmergencyDischarge> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<EmergencyDischarge> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<EmergencyDischarge> data) {
        List<EmergencyDischargeResponse> objects = new ArrayList<>();
        for (EmergencyDischarge p : data.getContent()) {
            objects.add(new EmergencyDischargeResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
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
