package com.kynsoft.propertyacqcenter.application.query.property.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchPropertyQueryHandler implements IQueryHandler<GetSearchPropertyQuery, PaginatedResponse>{

    private final IPropertyService propertyService;

    public GetSearchPropertyQueryHandler(IPropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @Override
    public PaginatedResponse handle(GetSearchPropertyQuery query) {

        return this.propertyService.search(query.getPageable(),query.getFilter());
    }
}
