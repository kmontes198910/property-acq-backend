package com.kynsof.evaluation.infrastructure.service;

import com.kynsof.evaluation.application.object.response.EvaluationResponse;
import com.kynsof.evaluation.domain.dto.EvaluationDto;
import com.kynsof.evaluation.domain.service.IEvaluationService;
import com.kynsof.evaluation.infrastructure.entity.Evaluation;
import com.kynsof.evaluation.infrastructure.repositories.command.EvaluationWriteDataJPARepository;
import com.kynsof.evaluation.infrastructure.repositories.query.EvaluationReadDataJPARepository;
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
public class EvaluationServiceImpl implements IEvaluationService {

    private final EvaluationWriteDataJPARepository repositoryCommand;

    private final EvaluationReadDataJPARepository repositoryQuery;

    public EvaluationServiceImpl(EvaluationWriteDataJPARepository repositoryCommand,
            EvaluationReadDataJPARepository repositoryQuery) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
    }

    @Override
    public void create(EvaluationDto object) {
        Evaluation serviceEntity = new Evaluation(object);
        this.repositoryCommand.save(serviceEntity);
    }

    @Override
    public void update(EvaluationDto objectDto) {
      Evaluation evaluation = this.repositoryQuery.findById(objectDto.getId()).orElseThrow();
      evaluation.setMedicalHistory(objectDto.getMedicalHistory());
      evaluation.setPhysicalExam(objectDto.getPhysicalExam());
      evaluation.setObservation(objectDto.getObservation());
      evaluation.setConsultationReason(objectDto.getConsultationReason());
        this.repositoryCommand.save(evaluation);
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
    public EvaluationDto findByIds(UUID id) {

        Optional<Evaluation> object = this.repositoryQuery.findById(id);
        if (object.isPresent()) {
            return object.get().toAggregate();
        }
        throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.SERVICE_NOT_FOUND, new ErrorField("id", "Service not found.")));

    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Evaluation> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Evaluation> data = this.repositoryQuery.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }



    private PaginatedResponse getPaginatedResponse(Page<Evaluation> data) {
        List<EvaluationResponse> servicesResponses = new ArrayList<>();
        for (Evaluation s : data.getContent()) {
            servicesResponses.add(new EvaluationResponse(s.toAggregate()));
        }
        return new PaginatedResponse(servicesResponses, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

}
