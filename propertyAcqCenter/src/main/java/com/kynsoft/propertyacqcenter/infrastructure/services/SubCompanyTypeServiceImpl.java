package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.SubCompanyTypeResponse;
import com.kynsoft.propertyacqcenter.domain.dto.SubCompanyTypeDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.ConstructionTypeNotFoundException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.NotDeleteException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.subCompanyType.CodeMustBeUniqueException;
import com.kynsoft.propertyacqcenter.infrastructure.entity.SubCompanyType;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import com.kynsoft.propertyacqcenter.domain.services.ISubCompanyTypeService;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.SubCompanyTypeReadDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.SubCompanyTypeWriteDataJPARepository;

@Service
public class SubCompanyTypeServiceImpl implements ISubCompanyTypeService {

    private final SubCompanyTypeReadDataJPARepository repositoryQuery;
    private final SubCompanyTypeWriteDataJPARepository repositoryCommand;

    public SubCompanyTypeServiceImpl(SubCompanyTypeReadDataJPARepository repositoryQuery, 
                                       SubCompanyTypeWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public UUID create(SubCompanyTypeDto object) {
        return repositoryCommand.save(new SubCompanyType(object)).getId();
    }

    @Override
    @Transactional
    public void update(SubCompanyTypeDto object) {
        SubCompanyType update = this.findByIdSimple(object.getId());

        update.setCode(object.getCode());
        update.setDescription(object.getDescription());
        update.setIsActive(object.getIsActive());
        update.setIsSpecialized(object.getIsSpecialized());
        update.setName(object.getName());
        update.setRequiresLicense(object.getRequiresLicense());
        update.setSpecializationArea(object.getSpecializationArea());
        update.setUpdatedAt(LocalDateTime.now());

        repositoryCommand.save(update);
    }

    @Override
    public void delete(UUID id) {
        try {
            this.findById(id);
            repositoryCommand.deleteById(id);
        } catch (Exception e) {
            throw new NotDeleteException();
        }
    }

    @Override
    public SubCompanyTypeDto findById(UUID id) {
        Optional<SubCompanyType> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get().toAggregateSimple();
        }
        throw new ConstructionTypeNotFoundException(id);
    }

    private SubCompanyType findByIdSimple(UUID id) {
        Optional<SubCompanyType> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        }
        throw new ConstructionTypeNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<SubCompanyType> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<SubCompanyType> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<SubCompanyType> data) {
        List<SubCompanyTypeResponse> objects = new ArrayList<>();
        for (SubCompanyType p : data.getContent()) {
            objects.add(new SubCompanyTypeResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public int countByCode(String code, UUID id) {
        return this.repositoryQuery.countByCode(code, id);
    }

    @Override
    public void validateCode(String code, UUID id) {
        int count = this.countByCode(code, id);
        if (count > 0) {
            throw new CodeMustBeUniqueException(code);
        }
    }

}
