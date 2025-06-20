package com.kynsoft.propertyacqcenter.application.query.adquisitionProperty.getByPropertyId;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FindByAdquisitionPropertyByPropertyIdQuery implements IQuery {

    private final String id;
}
