package com.kynsoft.propertyacqcenter.application.query.subCategoryConstructionType.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.ISubCategoryConstructionTypeService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchSubCategoryConstructionTypeQueryHandler implements IQueryHandler<GetSearchSubCategoryConstructionTypeQuery, PaginatedResponse>{

    private final ISubCategoryConstructionTypeService ISubCategoryConstructionTypeService;

    public GetSearchSubCategoryConstructionTypeQueryHandler(ISubCategoryConstructionTypeService ISubCategoryConstructionTypeService) {
        this.ISubCategoryConstructionTypeService = ISubCategoryConstructionTypeService;
    }

    @Override
    public PaginatedResponse handle(GetSearchSubCategoryConstructionTypeQuery query) {

        return this.ISubCategoryConstructionTypeService.search(query.getPageable(),query.getFilter());
    }
}
