package com.kynsoft.propertyacqcenter.application.query.titleCompany.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.stereotype.Component;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyService;
import org.springframework.beans.factory.annotation.Qualifier;

@Component
public class GetSearchTitleCompanyQueryHandler implements IQueryHandler<GetSearchTitleCompanyQuery, PaginatedResponse> {

    private final ICompanyService companyService;

    public GetSearchTitleCompanyQueryHandler(@Qualifier("titleCompanyService") ICompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    public PaginatedResponse handle(GetSearchTitleCompanyQuery query) {
        return this.companyService.search(query.getPageable(), query.getFilter());
    }
}
