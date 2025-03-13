package com.kynsof.evaluation.infrastructure.service;

import com.kynsof.evaluation.domain.dto.BusinessDto;
import com.kynsof.evaluation.domain.service.IBusinessService;
import com.kynsof.evaluation.infrastructure.entity.Business;
import com.kynsof.evaluation.infrastructure.repositories.command.BusinessWriteDataJPARepository;
import com.kynsof.evaluation.infrastructure.repositories.query.BusinessReadDataJPARepository;
import com.kynsof.share.core.domain.exception.BusinessException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class BusinessServiceImpl implements IBusinessService {

    @Autowired
    private BusinessWriteDataJPARepository repositoryCommand;

    @Autowired
    private BusinessReadDataJPARepository repositoryQuery;

    @Override
    public void create(BusinessDto object) {
        this.repositoryCommand.save(new Business(object));
    }

    @Override
    public void update(BusinessDto objectDto) {
        if (objectDto.getId() == null || objectDto == null) {
            throw new BusinessException(DomainErrorMessage.BUSINESS_OR_ID_NULL, "Business DTO or ID cannot be null.");
        }

        this.repositoryQuery.findById(objectDto.getId())
                .map(object -> {
                    if (objectDto.getName() != null) {
                        object.setName(objectDto.getName());
                    }
                    return this.repositoryCommand.save(object);
                })
                .orElseThrow(() -> new BusinessException(DomainErrorMessage.QUALIFICATION_NOT_FOUND, "Qualification not found."));

    }

    @Override
    public void delete(UUID id) {
        BusinessDto objectDelete = this.findById(id);

        this.repositoryCommand.save(new Business(objectDelete));
    }

    @Override
    public BusinessDto findById(UUID id) {

        Optional<Business> object = this.repositoryQuery.findById(id);
        if (object.isPresent()) {
            return object.get().toAggregate();
        }

        throw new BusinessException(DomainErrorMessage.BUSINESS_NOT_FOUND, "Business not found.");

    }

}
