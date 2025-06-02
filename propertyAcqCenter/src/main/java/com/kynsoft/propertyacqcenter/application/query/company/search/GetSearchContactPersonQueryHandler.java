package com.kynsoft.propertyacqcenter.application.query.company.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.stereotype.Component;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyService;

@Component
public class GetSearchContactPersonQueryHandler implements IQueryHandler<GetSearchContactPersonQuery, PaginatedResponse> {

    private final ICompanyService contactPersonService;

    public GetSearchContactPersonQueryHandler(ICompanyService contactPersonService) {
        this.contactPersonService = contactPersonService;
    }

    @Override
    public PaginatedResponse handle(GetSearchContactPersonQuery query) {
        return this.contactPersonService.search(query.getPageable(), query.getFilter());
    }
}
