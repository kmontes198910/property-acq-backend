package com.kynsoft.propertyacqcenter.application.query.contactPerson.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.IContactPersonService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchContactPersonQueryHandler implements IQueryHandler<GetSearchContactPersonQuery, PaginatedResponse> {

    private final IContactPersonService contactPersonService;

    public GetSearchContactPersonQueryHandler(IContactPersonService contactPersonService) {
        this.contactPersonService = contactPersonService;
    }

    @Override
    public PaginatedResponse handle(GetSearchContactPersonQuery query) {
        return this.contactPersonService.search(query.getPageable(), query.getFilter());
    }
}
