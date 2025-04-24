package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.ConstructionTypeResponse;
import com.kynsoft.propertyacqcenter.domain.dto.ConstructionTypeDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.ConstructionTypeNotFoundException;
import com.kynsoft.propertyacqcenter.domain.services.IConstructionTypeService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.ConstructionType;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.ConstructionTypeWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.ConstructionTypeReadDataJPARepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;

@Service
public class ConstructionTypeServiceImpl implements IConstructionTypeService {

    private final ConstructionTypeReadDataJPARepository repositoryQuery;
    private final ConstructionTypeWriteDataJPARepository repositoryCommand;

    public ConstructionTypeServiceImpl(ConstructionTypeReadDataJPARepository repositoryQuery, 
                                       ConstructionTypeWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public UUID create(ConstructionTypeDto object) {
        return repositoryCommand.save(new ConstructionType(object)).getId();
    }

    @Override
    @Transactional
    public void update(ConstructionTypeDto object) {
        ConstructionType update = this.findByIdSimple(object.getId());

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
    @Transactional
    public void delete(UUID id) {
        this.findById(id);
        repositoryCommand.deleteById(id);
    }

    @Override
    public ConstructionTypeDto findById(UUID id) {
        Optional<ConstructionType> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get().toAggregate();
        }
        throw new ConstructionTypeNotFoundException(id);
    }

    private ConstructionType findByIdSimple(UUID id) {
        Optional<ConstructionType> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        }
        throw new ConstructionTypeNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<ConstructionType> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<ConstructionType> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<ConstructionType> data) {
        List<ConstructionTypeResponse> objects = new ArrayList<>();
        for (ConstructionType p : data.getContent()) {
            objects.add(new ConstructionTypeResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

}
