package com.kynsof.treatments.infrastructure.service;

import com.kynsof.share.core.domain.exception.BusinessException;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsof.treatments.application.query.businessProcedure.search.BusinessProcedureResponse;
import com.kynsof.treatments.domain.dto.BusinessProcedureDto;
import com.kynsof.treatments.domain.service.IBusinessProcedureService;
import com.kynsof.treatments.infrastructure.entity.BusinessProcedure;
import com.kynsof.treatments.infrastructure.repositories.command.BusinessProcedureWriteDataJPARepository;
import com.kynsof.treatments.infrastructure.repositories.query.BusinessProcedureReadDataJPARepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BusinessProcedureServiceImpl implements IBusinessProcedureService {

    private final BusinessProcedureWriteDataJPARepository repositoryCommand;
    private final BusinessProcedureReadDataJPARepository repositoryQuery;

    public BusinessProcedureServiceImpl(BusinessProcedureWriteDataJPARepository repositoryCommand,
                                        BusinessProcedureReadDataJPARepository repositoryQuery) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
    }

    @Override
    public void create(List<BusinessProcedureDto> objects) {
        List<BusinessProcedure> businessProcedures = objects.stream().map(BusinessProcedure::new).collect(Collectors.toList());
        repositoryCommand.saveAll(businessProcedures);
    }

    @Override
    public void update(BusinessProcedureDto objectDto) {
        if (objectDto == null || objectDto.getId() == null) {
            throw new BusinessException(DomainErrorMessage.BUSINESS_OR_ID_NULL, "Business DTO or ID cannot be null.");
        }
        BusinessProcedure business = new BusinessProcedure(objectDto);
        repositoryCommand.save(business);
    }

    @Override
    public void delete(UUID id) {
        try {
            repositoryCommand.deleteById(id);
        } catch (Exception e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(
                    DomainErrorMessage.NOT_DELETE,
                    new ErrorField("id", "Element cannot be deleted as it has a related element.")));
        }
    }

    @Override
    public void deleteIds(List<UUID> ids) {
        repositoryCommand.deleteAllByIdInBatch(ids);
    }

    @Override
    public BusinessProcedureDto findById(UUID id) {
        return repositoryQuery.findById(id)
                .map(BusinessProcedure::toAggregate)
                .orElseThrow(() -> new BusinessNotFoundException(new GlobalBusinessException(
                        DomainErrorMessage.BUSINESS_NOT_FOUND,
                        new ErrorField("id", "Business not found."))));
    }

    @Override
    public List<BusinessProcedureDto> findByCodes(UUID businessId, List<String> codes) {
        List<BusinessProcedure> businessProcedures = repositoryQuery.findByCodesAndBusiness(codes,businessId);
        return businessProcedures.stream().map(BusinessProcedure::toAggregate).collect(Collectors.toList());
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<BusinessProcedure> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<BusinessProcedure> data = repositoryQuery.findAll(specifications, pageable);
        return createPaginatedResponse(data);
    }

    private PaginatedResponse createPaginatedResponse(Page<BusinessProcedure> data) {
        List<BusinessProcedureResponse> collect = data.getContent().stream()
                .map(BusinessProcedure::toAggregate)
                .map(BusinessProcedureResponse::new)
                .collect(Collectors.toList());

        return new PaginatedResponse(collect, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

}