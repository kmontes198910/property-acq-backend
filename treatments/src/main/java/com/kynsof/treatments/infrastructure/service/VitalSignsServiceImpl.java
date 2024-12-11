package com.kynsof.treatments.infrastructure.service;

import com.kynsof.share.core.domain.exception.BusinessException;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsof.treatments.application.query.vitalsigns.search.VitalSignsResponse;
import com.kynsof.treatments.domain.dto.VitalSignsDto;
import com.kynsof.treatments.domain.service.IVitalSignsService;
import com.kynsof.treatments.infrastructure.entity.VitalSigns;
import com.kynsof.treatments.infrastructure.repositories.command.VitalSignsWriteDataJPARepository;
import com.kynsof.treatments.infrastructure.repositories.query.VitalSingsReadDataJPARepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VitalSignsServiceImpl implements IVitalSignsService {

    private final VitalSignsWriteDataJPARepository repositoryCommand;
    private final VitalSingsReadDataJPARepository repositoryQuery;

    public VitalSignsServiceImpl(VitalSignsWriteDataJPARepository repositoryCommand,
                                 VitalSingsReadDataJPARepository repositoryQuery) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
    }

    @Override
    public void create(VitalSignsDto objects) {
        repositoryCommand.save(new VitalSigns(objects));
    }

    @Override
    public void update(VitalSignsDto objectDto) {
        if (objectDto == null || objectDto.getId() == null) {
            throw new BusinessException(DomainErrorMessage.BUSINESS_OR_ID_NULL, "Business DTO or ID cannot be null.");
        }
        VitalSigns vitalSigns = new VitalSigns(objectDto);
        repositoryCommand.save(vitalSigns);
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
    public VitalSignsDto findById(UUID id) {
        return repositoryQuery.findById(id)
                .map(VitalSigns::toAggregate)
                .orElseThrow(() -> new BusinessNotFoundException(new GlobalBusinessException(
                        DomainErrorMessage.BUSINESS_NOT_FOUND,
                        new ErrorField("id", "Business not found."))));
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<VitalSigns> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<VitalSigns> data = repositoryQuery.findAll(specifications, pageable);
        return createPaginatedResponse(data);
    }

    private PaginatedResponse createPaginatedResponse(Page<VitalSigns> data) {
        List<VitalSignsResponse> collect = data.getContent().stream()
                .map(VitalSigns::toAggregate)
                .map(VitalSignsResponse::new)
                .collect(Collectors.toList());

        return new PaginatedResponse(collect, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

}