package com.kynsoft.propertyacqcenter.application.query.sales.getByPropertyId;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FindBySalesByPropertyIdQuery implements IQuery {

    private final String id;
}
