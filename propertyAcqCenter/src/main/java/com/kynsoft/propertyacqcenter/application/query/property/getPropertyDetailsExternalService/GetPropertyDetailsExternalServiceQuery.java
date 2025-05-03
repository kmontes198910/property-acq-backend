package com.kynsoft.propertyacqcenter.application.query.property.getPropertyDetailsExternalService;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetPropertyDetailsExternalServiceQuery implements IQuery {
    private String address;
}
