package com.kynsof.treatments.infrastructure.service;

import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsof.treatments.application.query.diagnosis.getbyid.DiagnosisResponse;
import com.kynsof.treatments.domain.dto.DiagnosisDto;
import com.kynsof.treatments.domain.dto.ExternalConsultationDto;
import com.kynsof.treatments.domain.service.IDiagnosisService;
import com.kynsof.treatments.infrastructure.entity.Diagnosis;
import com.kynsof.treatments.infrastructure.entity.ExternalConsultation;
import com.kynsof.treatments.infrastructure.repositories.command.DiagnosisWriteDataJPARepository;
import com.kynsof.treatments.infrastructure.repositories.query.DiagnosisReadDataJPARepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DiagnosisServiceImpl implements IDiagnosisService {

    private final DiagnosisReadDataJPARepository repositoryQuery;
    private final DiagnosisWriteDataJPARepository repositoryCommand;

    public DiagnosisServiceImpl(DiagnosisReadDataJPARepository repositoryQuery,
                                DiagnosisWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    public void create(DiagnosisDto diagnosisDtoList) {
      repositoryCommand.save(new Diagnosis(diagnosisDtoList));
    }

    @Override
    public void update(DiagnosisDto treatment) {
        Diagnosis update = new Diagnosis(treatment);
        update.setUpdatedAt(LocalDateTime.now());
        repositoryCommand.save(update);
    }

    @Override
    public void delete(DiagnosisDto treatment) {
        try {
            repositoryCommand.deleteById(treatment.getId());
        } catch (Exception e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(
                    DomainErrorMessage.NOT_DELETE,
                    new ErrorField("id", "Element cannot be deleted as it has a related element.")));
        }
    }

    @Override
    public void deleteByIds(List<UUID> ids) {
        repositoryCommand.deleteAllByIdInBatch(ids);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        var specifications = new GenericSpecificationsBuilder<Diagnosis>(filterCriteria);
        var data = repositoryQuery.findAll(specifications, pageable);
        return createPaginatedResponse(data);
    }

    private PaginatedResponse createPaginatedResponse(Page<Diagnosis> data) {
        var responses = data.getContent().stream()
                .map(Diagnosis::toAggregate)
                .map(DiagnosisResponse::new)
                .collect(Collectors.toList());
        return new PaginatedResponse(responses, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public DiagnosisDto findById(UUID id) {
        return repositoryQuery.findById(id)
                .map(Diagnosis::toAggregate)
                .orElseThrow(() -> new BusinessNotFoundException(new GlobalBusinessException(
                        DomainErrorMessage.DIAGNOSIS_NOT_FOUND,
                        new ErrorField("id", "Diagnosis not found."))));
    }

    @Override
    public PaginatedResponse findByExternalConsultation(ExternalConsultationDto externalConsultation, Pageable pageable) {
        var diagnosis = repositoryQuery.findByExternalConsultation(new ExternalConsultation(externalConsultation), pageable);
        return createPaginatedResponse(diagnosis);
    }
}