package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.SubCategoryConstructionTypeResponse;
import com.kynsoft.propertyacqcenter.domain.dto.SubCategoryConstructionTypeDto;
import com.kynsoft.propertyacqcenter.domain.services.ISubCategoryConstructionTypeService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.SubCategoryConstructionType;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.SubCategoryConstructionTypeWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.SubCategoryConstructionTypeReadDataJPARepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;

@Service
public class SubCategoryConstructionTypeServiceImpl implements ISubCategoryConstructionTypeService {

    private final SubCategoryConstructionTypeReadDataJPARepository repositoryQuery;
    private final SubCategoryConstructionTypeWriteDataJPARepository repositoryCommand;

    public SubCategoryConstructionTypeServiceImpl(SubCategoryConstructionTypeReadDataJPARepository repositoryQuery, 
                                                       SubCategoryConstructionTypeWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public UUID create(SubCategoryConstructionTypeDto object) {
        return null;
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<SubCategoryConstructionType> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<SubCategoryConstructionType> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<SubCategoryConstructionType> data) {
        List<SubCategoryConstructionTypeResponse> objects = new ArrayList<>();
        for (SubCategoryConstructionType p : data.getContent()) {
            objects.add(new SubCategoryConstructionTypeResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

}
