package com.kynsof.treatments.application.service;

import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsof.treatments.application.query.paymentAllergy.getbyid.PaymentAllergyResponse;
import com.kynsof.treatments.domain.dto.PathologicalHistoryDto;
import com.kynsof.treatments.domain.service.IPatientAllergyService;
import com.kynsof.treatments.infrastructure.entity.PathologicalHistory;
import com.kynsof.treatments.infrastructure.repositories.command.PatientAllergyWriteDataJPARepository;
import com.kynsof.treatments.infrastructure.repositories.query.PatientAllergyReadDataJPARepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PatientAllergyServiceImpl implements IPatientAllergyService {

    private final PatientAllergyWriteDataJPARepository repositoryCommand;
    private final PatientAllergyReadDataJPARepository repositoryQuery;

    public PatientAllergyServiceImpl(PatientAllergyReadDataJPARepository repositoryQuery,
                                     PatientAllergyWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    public UUID create(PathologicalHistoryDto diagnosisDtoList) {
        PathologicalHistory entity = new PathologicalHistory(diagnosisDtoList);
        PathologicalHistory allergy =  repositoryCommand.save(entity);
        return allergy.getId();
    }

    @Override
    public void update(PathologicalHistoryDto treatment) {
        PathologicalHistory update = new PathologicalHistory(treatment);
        repositoryCommand.save(update);
    }

    @Override
    public void delete(PathologicalHistoryDto treatment) {
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
        var specifications = new GenericSpecificationsBuilder<PathologicalHistory>(filterCriteria);
        var data = repositoryQuery.findAll(specifications, pageable);
        return createPaginatedResponse(data);
    }

    private PaginatedResponse createPaginatedResponse(Page<PathologicalHistory> data) {
        var responses = data.getContent().stream()
                .map(PathologicalHistory::toAggregate)
                .map(PaymentAllergyResponse::new)
                .collect(Collectors.toList());
        return new PaginatedResponse(responses, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public PathologicalHistoryDto findById(UUID id) {
        return repositoryQuery.findById(id)
                .map(PathologicalHistory::toAggregate)
                .orElseThrow(() -> new BusinessNotFoundException(new GlobalBusinessException(
                        DomainErrorMessage.DIAGNOSIS_NOT_FOUND,
                        new ErrorField("id", "PatientAllergy not found."))));
    }

}