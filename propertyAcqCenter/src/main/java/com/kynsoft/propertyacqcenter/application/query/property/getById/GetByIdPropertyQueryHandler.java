package com.kynsoft.propertyacqcenter.application.query.property.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.PropertiesGetByIdResponse;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
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

        PropertyDto property = this.propertyService.getById(query.getId());
        Double afterRepairValue = property.getAfterRepairValue();

        try {
            SalesPropertyDto sales = this.salesPropertyService.findByPropertyId(query.getId());
            afterRepairValue = sales.getAfterRepairValue();
        } catch (Exception e) {
            System.err.println("##############################################");
            System.err.println("##############################################");
            System.err.println("AfterRepairValue not found");
            System.err.println("##############################################");
            System.err.println("##############################################");
        }

        return new PropertiesGetByIdResponse(property, afterRepairValue);
    }
}
