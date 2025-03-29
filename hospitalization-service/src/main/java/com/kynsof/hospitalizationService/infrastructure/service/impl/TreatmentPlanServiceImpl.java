package com.kynsof.hospitalizationService.infrastructure.service.impl;

import com.kynsof.hospitalizationService.application.response.TreatmentPlanResponse;
import com.kynsof.hospitalizationService.domain.dto.TreatmentPlanDto;
import com.kynsof.hospitalizationService.domain.dto.exception.TreatmentPlanNotFoundException;
import com.kynsof.hospitalizationService.domain.service.ITreatmentPlanService;
import com.kynsof.hospitalizationService.infrastructure.entity.TreatmentPlan;
import com.kynsof.hospitalizationService.infrastructure.repositories.command.TreatmentPlanWriteDataJPARepository;
import com.kynsof.hospitalizationService.infrastructure.repositories.query.TreatmentPlanReadDataJPARepository;
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
public class TreatmentPlanServiceImpl implements ITreatmentPlanService {

    private final TreatmentPlanWriteDataJPARepository repositoryCommand;
    private final TreatmentPlanReadDataJPARepository repositoryQuery;

    public TreatmentPlanServiceImpl(TreatmentPlanWriteDataJPARepository repositoryCommand, TreatmentPlanReadDataJPARepository repositoryQuery) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
    }

    @Override
    @Transactional
    public UUID create(TreatmentPlanDto dto) {
        TreatmentPlan object = new TreatmentPlan(dto);
        this.repositoryCommand.save(object);
        return dto.getId();
    }

    @Override
    @Transactional
    public UUID update(TreatmentPlanDto dto) {
        if (dto == null || dto.getId() == null) {
            throw new IllegalArgumentException("Emergency Case or ID cannot be null");
        }

        TreatmentPlan update = new TreatmentPlan(dto);
        this.repositoryCommand.save(update);
        return dto.getId();
    }

    @Override
    public TreatmentPlanDto findById(UUID id) {
        Optional<TreatmentPlan> contactInformation = this.repositoryQuery.findById(id);
        if (contactInformation.isPresent()) {
            return contactInformation.get().toAggregate();
        }
        throw new TreatmentPlanNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<TreatmentPlan> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<TreatmentPlan> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<TreatmentPlan> data) {
        List<TreatmentPlanResponse> patients = new ArrayList<>();
        for (TreatmentPlan p : data.getContent()) {
            patients.add(new TreatmentPlanResponse(p.toAggregate()));
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
