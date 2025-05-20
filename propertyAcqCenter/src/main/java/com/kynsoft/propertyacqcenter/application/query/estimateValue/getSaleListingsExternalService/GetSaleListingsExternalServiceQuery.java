package com.kynsoft.propertyacqcenter.application.query.estimateValue.getSaleListingsExternalService;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetSaleListingsExternalServiceQuery implements IQuery {
    private String address;
}
