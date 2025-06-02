package com.kynsoft.propertyacqcenter.application.query.companyContact.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyContactService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchCompanyContactQueryHandler implements IQueryHandler<GetSearchCompanyContactQuery, PaginatedResponse>{

    private final ICompanyContactService companyContactService;

    public GetSearchCompanyContactQueryHandler(ICompanyContactService companyContactService) {
        this.companyContactService = companyContactService;
    }

    @Override
    public PaginatedResponse handle(GetSearchCompanyContactQuery query) {

        return this.companyContactService.search(query.getPageable(),query.getFilter());
    }
}
