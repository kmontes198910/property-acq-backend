package com.kynsoft.propertyacqcenter.application.query.constructionType.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.IConstructionTypeService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchConstructionTypeQueryHandler implements IQueryHandler<GetSearchConstructionTypeQuery, PaginatedResponse>{

    private final IConstructionTypeService constructionTypeService;

    public GetSearchConstructionTypeQueryHandler(IConstructionTypeService constructionTypeService) {
        this.constructionTypeService = constructionTypeService;
    }

    @Override
    public PaginatedResponse handle(GetSearchConstructionTypeQuery query) {

        return this.constructionTypeService.search(query.getPageable(),query.getFilter());
    }
}
