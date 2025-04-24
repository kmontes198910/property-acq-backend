package com.kynsoft.propertyacqcenter.application.query.contact.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.IContactService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchContactQueryHandler implements IQueryHandler<GetSearchContactQuery, PaginatedResponse> {

    private final IContactService contactService;

    public GetSearchContactQueryHandler(IContactService contactService) {
        this.contactService = contactService;
    }

    @Override
    public PaginatedResponse handle(GetSearchContactQuery query) {
        return this.contactService.search(query.getPageable(), query.getFilter());
    }
}
