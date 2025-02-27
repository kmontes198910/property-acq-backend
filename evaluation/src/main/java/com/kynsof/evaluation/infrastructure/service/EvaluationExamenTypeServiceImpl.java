package com.kynsof.evaluation.infrastructure.service;

import com.kynsof.evaluation.application.object.response.EvaluationExamenTypeResponse;
import com.kynsof.evaluation.domain.dto.EvaluationExamenTypeDto;
import com.kynsof.evaluation.domain.service.IEvaluationExamenTypeService;
import com.kynsof.evaluation.infrastructure.entity.EvaluationExamenType;
import com.kynsof.evaluation.infrastructure.repositories.command.EvaluationExamenTypeWriteDataJPARepository;
import com.kynsof.evaluation.infrastructure.repositories.query.EvaluationExamenTypeReadDataJPARepository;
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
public class EvaluationExamenTypeServiceImpl implements IEvaluationExamenTypeService {

    private final EvaluationExamenTypeWriteDataJPARepository repositoryCommand;

    private final EvaluationExamenTypeReadDataJPARepository repositoryQuery;

    public EvaluationExamenTypeServiceImpl(EvaluationExamenTypeWriteDataJPARepository repositoryCommand,
            EvaluationExamenTypeReadDataJPARepository repositoryQuery) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
    }

    @Override
    public void create(EvaluationExamenTypeDto object) {
        EvaluationExamenType serviceEntity = new EvaluationExamenType(object);
        this.repositoryCommand.save(serviceEntity);
    }

    @Override
    public void update(EvaluationExamenTypeDto objectDto) {
        EvaluationExamenType update = new EvaluationExamenType(objectDto);
        update.setUpdatedAt(LocalDateTime.now());
        this.repositoryCommand.save(update);
    }

    @Override
    public void delete(UUID id) {
        try {
            this.repositoryCommand.deleteById(id);
        } catch (Exception e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.NOT_DELETE, new ErrorField("id", "Element cannot be deleted has a related element.")));
        }
    }

    @Override
    public EvaluationExamenTypeDto findByIds(UUID id) {

        Optional<EvaluationExamenType> object = this.repositoryQuery.findById(id);
        if (object.isPresent()) {
            return object.get().toAggregate();
        }
        throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.SERVICE_NOT_FOUND, new ErrorField("id", "Service not found.")));

    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Service> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<EvaluationExamenType> data = this.repositoryQuery.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<EvaluationExamenType> data) {
        List<EvaluationExamenTypeResponse> servicesResponses = new ArrayList<>();
        for (EvaluationExamenType s : data.getContent()) {
            servicesResponses.add(new EvaluationExamenTypeResponse(s.toAggregate()));
        }
        return new PaginatedResponse(servicesResponses, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

}
