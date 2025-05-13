package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.SubCategoryRealEstateCompanyTypeResponse;
import com.kynsoft.propertyacqcenter.domain.dto.SubCategoryRealEstateCompanyTypeDto;
import com.kynsoft.propertyacqcenter.domain.services.ISubCategoryRealEstateCompanyTypeService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.SubCategoryRealEstateCompanyType;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.SubCategoryRealEstateCompanyTypeWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.SubCategoryRealEstateCompanyTypeReadDataJPARepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;

@Service
public class SubCategoryRealEstateCompanyTypeServiceImpl implements ISubCategoryRealEstateCompanyTypeService {

    private final SubCategoryRealEstateCompanyTypeReadDataJPARepository repositoryQuery;
    private final SubCategoryRealEstateCompanyTypeWriteDataJPARepository repositoryCommand;

    public SubCategoryRealEstateCompanyTypeServiceImpl(SubCategoryRealEstateCompanyTypeReadDataJPARepository repositoryQuery, 
                                                       SubCategoryRealEstateCompanyTypeWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public UUID create(SubCategoryRealEstateCompanyTypeDto object) {
        return null;
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<SubCategoryRealEstateCompanyType> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<SubCategoryRealEstateCompanyType> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<SubCategoryRealEstateCompanyType> data) {
        List<SubCategoryRealEstateCompanyTypeResponse> objects = new ArrayList<>();
        for (SubCategoryRealEstateCompanyType p : data.getContent()) {
            objects.add(new SubCategoryRealEstateCompanyTypeResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

}
