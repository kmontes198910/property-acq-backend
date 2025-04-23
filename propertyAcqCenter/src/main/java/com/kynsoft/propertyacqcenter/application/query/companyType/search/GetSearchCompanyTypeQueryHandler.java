package com.kynsoft.propertyacqcenter.application.query.companyType.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyTypeService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchCompanyTypeQueryHandler implements IQueryHandler<GetSearchCompanyTypeQuery, PaginatedResponse>{

    private final ICompanyTypeService companyTypeService;

    public GetSearchCompanyTypeQueryHandler(ICompanyTypeService companyTypeService) {
        this.companyTypeService = companyTypeService;
    }

    @Override
    public PaginatedResponse handle(GetSearchCompanyTypeQuery query) {

        return this.companyTypeService.search(query.getPageable(),query.getFilter());
    }
}
