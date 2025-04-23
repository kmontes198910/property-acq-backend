package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.CompanyTypeResponse;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyTypeDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.CompanyTypeNotFoundException;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyTypeService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.CompanyType;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.CompanyTypeWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.CompanyTypeReadDataJPARepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;

@Service
public class CompanyTypeServiceImpl implements ICompanyTypeService {

    private final CompanyTypeReadDataJPARepository repositoryQuery;
    private final CompanyTypeWriteDataJPARepository repositoryCommand;

    public CompanyTypeServiceImpl(
            CompanyTypeReadDataJPARepository repositoryQuery,
            CompanyTypeWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public UUID create(CompanyTypeDto companyTypeDto) {
        return repositoryCommand.save(new CompanyType(companyTypeDto)).getId();
    }

    @Override
    @Transactional
    public void update(CompanyTypeDto companyTypeDto) {
        Optional<CompanyType> existingCompanyType = repositoryQuery.findById(companyTypeDto.getId());
        if (existingCompanyType.isPresent()) {
            CompanyType companyType = existingCompanyType.get();

            companyType.setName(companyTypeDto.getName());
            companyType.setCode(companyTypeDto.getCode());
            companyType.setDescription(companyTypeDto.getDescription());
            companyType.setExamples(companyTypeDto.getExamples());
            companyType.setIsActive(companyTypeDto.getIsActive());
            companyType.setUpdatedAt(LocalDateTime.now());

            repositoryCommand.save(companyType);
        } else {
            throw new CompanyTypeNotFoundException(companyTypeDto.getId());
        }
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        this.findById(id);
        repositoryCommand.deleteById(id);
    }

    @Override
    public CompanyTypeDto findById(UUID id) {
        Optional<CompanyType> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get().toAggregate();
        }
        throw new CompanyTypeNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<CompanyType> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<CompanyType> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<CompanyType> data) {
        List<CompanyTypeResponse> objects = new ArrayList<>();
        for (CompanyType p : data.getContent()) {
            objects.add(new CompanyTypeResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

}