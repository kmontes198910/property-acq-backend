package com.kynsoft.propertyacqcenter.application.query.property.getPropertiesByIdContact;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.domain.dto.projection.PropertyWithProfileDTO;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GetPropertiesByIdContactQueryHandler implements IQueryHandler<GetPropertiesByIdContactQuery, ListPropertyWithProfileResponse>{

    private final IPropertyService propertyService;

    public GetPropertiesByIdContactQueryHandler(IPropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @Override
    public ListPropertyWithProfileResponse handle(GetPropertiesByIdContactQuery query) {
        List<PropertyWithProfileDTO> list = this.propertyService.findPropertiesWithProfileByContact(query.getId());

        List<PropertyWithProfileResponse> response = new ArrayList<>();
        for (PropertyWithProfileDTO propertyWithProfileDTO : list) {
            response.add(new PropertyWithProfileResponse(propertyWithProfileDTO));
        }

        return new ListPropertyWithProfileResponse(response);
    }
}
