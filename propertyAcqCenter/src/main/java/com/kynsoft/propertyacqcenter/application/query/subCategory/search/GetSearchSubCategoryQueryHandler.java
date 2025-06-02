package com.kynsoft.propertyacqcenter.application.query.subCategory.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.stereotype.Component;
import com.kynsoft.propertyacqcenter.domain.services.ISubCategoryService;

@Component
public class GetSearchSubCategoryQueryHandler implements IQueryHandler<GetSearchSubCategoryQuery, PaginatedResponse>{

    private final ISubCategoryService ISubCategoryConstructionTypeService;

    public GetSearchSubCategoryQueryHandler(ISubCategoryService ISubCategoryConstructionTypeService) {
        this.ISubCategoryConstructionTypeService = ISubCategoryConstructionTypeService;
    }

    @Override
    public PaginatedResponse handle(GetSearchSubCategoryQuery query) {

        return this.ISubCategoryConstructionTypeService.search(query.getPageable(),query.getFilter());
    }
}
