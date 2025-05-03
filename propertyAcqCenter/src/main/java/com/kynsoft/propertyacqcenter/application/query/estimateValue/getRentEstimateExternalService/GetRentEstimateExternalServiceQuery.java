package com.kynsoft.propertyacqcenter.application.query.estimateValue.getRentEstimateExternalService;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetRentEstimateExternalServiceQuery implements IQuery {
    private String address;
}
