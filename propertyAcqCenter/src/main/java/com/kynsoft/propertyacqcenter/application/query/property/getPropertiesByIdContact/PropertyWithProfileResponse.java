package com.kynsoft.propertyacqcenter.application.query.property.getPropertiesByIdContact;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.application.response.PropertiesProfileResponse;
import com.kynsoft.propertyacqcenter.domain.dto.projection.PropertyWithProfileDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PropertyWithProfileResponse implements IResponse {
    private PropertiesProfileResponse property;
    private String profile;

    public PropertyWithProfileResponse(PropertyWithProfileDTO dto) {
        this.property = new PropertiesProfileResponse(dto.getProperty().toAggregate());
        this.profile = dto.getProfile();
    }

}
