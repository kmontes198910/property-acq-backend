package com.kynsoft.propertyacqcenter.application.query.property.getPropertiesByIdContact;

import com.kynsof.share.core.domain.bus.query.IResponse;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ListPropertyWithProfileResponse implements IResponse {
    private List<PropertyWithProfileResponse> list;

    public ListPropertyWithProfileResponse(List<PropertyWithProfileResponse> list) {
        this.list = list;
    }

}
