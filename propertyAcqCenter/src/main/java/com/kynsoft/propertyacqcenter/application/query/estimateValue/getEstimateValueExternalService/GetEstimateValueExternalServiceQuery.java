package com.kynsoft.propertyacqcenter.application.query.estimateValue.getEstimateValueExternalService;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetEstimateValueExternalServiceQuery implements IQuery {
    private String address;
}
