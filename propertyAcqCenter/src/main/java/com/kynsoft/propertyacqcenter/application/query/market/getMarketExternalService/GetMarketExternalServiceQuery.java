package com.kynsoft.propertyacqcenter.application.query.market.getMarketExternalService;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetMarketExternalServiceQuery implements IQuery {

    private String dataType;
    private int historyRange;
    private String zipCode;
}
