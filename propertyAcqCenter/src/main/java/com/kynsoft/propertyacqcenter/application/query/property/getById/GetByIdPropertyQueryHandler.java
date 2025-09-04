package com.kynsoft.propertyacqcenter.application.query.property.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.PropertiesGetByIdResponse;
import com.kynsoft.propertyacqcenter.domain.dto.SalesPropertyDto;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import com.kynsoft.propertyacqcenter.domain.services.ISalesPropertyService;
import org.springframework.stereotype.Component;

@Component
public class GetByIdPropertyQueryHandler implements IQueryHandler<GetByIdPropertyQuery, PropertiesGetByIdResponse>{

    private final IPropertyService propertyService;
    private final ISalesPropertyService salesPropertyService;

    public GetByIdPropertyQueryHandler(IPropertyService propertyService, ISalesPropertyService salesPropertyService) {
        this.propertyService = propertyService;
        this.salesPropertyService = salesPropertyService;
    }

    @Override
    public PropertiesGetByIdResponse handle(GetByIdPropertyQuery query) {
        
        SalesPropertyDto sales = this.salesPropertyService.findByPropertyId(query.getId());
        return new PropertiesGetByIdResponse(this.propertyService.getById(query.getId()), sales.getAfterRepairValue());
    }
}
