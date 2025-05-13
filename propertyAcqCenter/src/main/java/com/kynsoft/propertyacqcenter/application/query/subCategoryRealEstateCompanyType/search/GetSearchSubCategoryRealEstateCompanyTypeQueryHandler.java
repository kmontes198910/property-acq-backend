package com.kynsoft.propertyacqcenter.application.query.subCategoryRealEstateCompanyType.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.ISubCategoryRealEstateCompanyTypeService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchSubCategoryRealEstateCompanyTypeQueryHandler implements IQueryHandler<GetSearchSubCategoryRealEstateCompanyTypeQuery, PaginatedResponse>{

    private final ISubCategoryRealEstateCompanyTypeService subCategoryRealEstateCompanyTypeService;

    public GetSearchSubCategoryRealEstateCompanyTypeQueryHandler(ISubCategoryRealEstateCompanyTypeService subCategoryRealEstateCompanyTypeService) {
        this.subCategoryRealEstateCompanyTypeService = subCategoryRealEstateCompanyTypeService;
    }

    @Override
    public PaginatedResponse handle(GetSearchSubCategoryRealEstateCompanyTypeQuery query) {

        return this.subCategoryRealEstateCompanyTypeService.search(query.getPageable(),query.getFilter());
    }
}
