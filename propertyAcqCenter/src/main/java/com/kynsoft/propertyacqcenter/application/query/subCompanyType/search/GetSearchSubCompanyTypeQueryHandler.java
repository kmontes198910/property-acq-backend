package com.kynsoft.propertyacqcenter.application.query.subCompanyType.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.stereotype.Component;
import com.kynsoft.propertyacqcenter.domain.services.ISubCompanyTypeService;

@Component
public class GetSearchSubCompanyTypeQueryHandler implements IQueryHandler<GetSearchSubCompanyTypeQuery, PaginatedResponse>{

    private final ISubCompanyTypeService constructionTypeService;

    public GetSearchSubCompanyTypeQueryHandler(ISubCompanyTypeService constructionTypeService) {
        this.constructionTypeService = constructionTypeService;
    }

    @Override
    public PaginatedResponse handle(GetSearchSubCompanyTypeQuery query) {

        return this.constructionTypeService.search(query.getPageable(),query.getFilter());
    }
}
