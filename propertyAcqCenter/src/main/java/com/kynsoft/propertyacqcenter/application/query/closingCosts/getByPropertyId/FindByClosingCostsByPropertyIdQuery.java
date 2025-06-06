package com.kynsoft.propertyacqcenter.application.query.closingCosts.getByPropertyId;

import com.kynsoft.propertyacqcenter.application.query.purchase.getByPropertyId.*;
import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FindByClosingCostsByPropertyIdQuery implements IQuery {

    private final String id;
}
