package com.kynsof.payment.application.service;

import com.kynsof.payment.application.query.business.search.BusinessResponse;
import com.kynsof.payment.domain.dto.BusinessDto;
import com.kynsof.payment.domain.service.IBusiness;
import com.kynsof.payment.infrastructure.entity.Business;
import com.kynsof.payment.infrastructure.repositories.command.BusinessWriteDataJPARepository;
import com.kynsof.payment.infrastructure.repositories.query.BusinessReadDataJPARepository;
import com.kynsof.share.core.domain.exception.BusinessException;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BusinessServiceImpl implements IBusiness {

    private final BusinessWriteDataJPARepository repositoryCommand;
    private final BusinessReadDataJPARepository repositoryQuery;

    public BusinessServiceImpl(BusinessWriteDataJPARepository repositoryCommand,
                               BusinessReadDataJPARepository repositoryQuery) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
    }

    @Override
    public void create(BusinessDto object) {
        repositoryCommand.save(new Business(object));
    }

    @Override
    public void update(BusinessDto objectDto) {
        if (objectDto == null || objectDto.getId() == null) {
            throw new BusinessException(DomainErrorMessage.BUSINESS_OR_ID_NULL, "Business DTO or ID cannot be null.");
        }

        Business business = repositoryQuery.findById(objectDto.getId())
                .orElseThrow(() -> new BusinessException(DomainErrorMessage.BUSINESS_NOT_FOUND, "Business not found."));

        // Actualización de campos
        Optional.ofNullable(objectDto.getName()).ifPresent(business::setName);
        Optional.ofNullable(objectDto.getLogo()).ifPresent(business::setLogo);

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
    public BusinessDto findById(UUID id) {
        return repositoryQuery.findById(id)
                .map(Business::toAggregate)
                .orElseThrow(() -> new BusinessNotFoundException(new GlobalBusinessException(
                        DomainErrorMessage.BUSINESS_NOT_FOUND,
                        new ErrorField("id", "Business not found."))));
    }


    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Business> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Business> data = repositoryQuery.findAll(specifications, pageable);
        return createPaginatedResponse(data);
    }

    private PaginatedResponse createPaginatedResponse(Page<Business> data) {
        List<BusinessResponse> businessResponses = data.getContent().stream()
                .map(Business::toAggregate)
                .map(BusinessResponse::new)
                .collect(Collectors.toList());

        return new PaginatedResponse(businessResponses, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public List<BusinessDto> findAll() {
        return repositoryQuery.findAll().stream()
                .map(Business::toAggregate)
                .collect(Collectors.toList());
    }
}