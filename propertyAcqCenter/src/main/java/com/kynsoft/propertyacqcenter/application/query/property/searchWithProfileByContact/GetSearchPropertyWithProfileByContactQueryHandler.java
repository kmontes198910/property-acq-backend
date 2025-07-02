package com.kynsoft.propertyacqcenter.application.query.property.searchWithProfileByContact;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchPropertyWithProfileByContactQueryHandler implements IQueryHandler<GetSearchPropertyWithProfileByContactQuery, PaginatedResponse>{

    private final IPropertyService propertyService;

    public GetSearchPropertyWithProfileByContactQueryHandler(IPropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @Override
    public PaginatedResponse handle(GetSearchPropertyWithProfileByContactQuery query) {

        return this.propertyService.searchWithProfileByContact(query.getPageable(),query.getFilter());
    }
}
