package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import lombok.*;

@Setter
@Getter
public class PropertiesBasicResponse implements IResponse {

    private String id;
    private String formattedAddress;

    public PropertiesBasicResponse(PropertyDto dto) {
        this.id = dto.getId();
        this.formattedAddress = dto.getFormattedAddress();
    }

}