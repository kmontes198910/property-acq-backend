package com.kynsoft.propertyacqcenter.application.query.property.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.PropertiesResponse;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import org.springframework.stereotype.Component;

@Component
public class GetByIdPropertyQueryHandler implements IQueryHandler<GetByIdPropertyQuery, PropertiesResponse>{

    private final IPropertyService propertyService;

    public GetByIdPropertyQueryHandler(IPropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @Override
    public PropertiesResponse handle(GetByIdPropertyQuery query) {

        return new PropertiesResponse(this.propertyService.getById(query.getId()));
    }
}
