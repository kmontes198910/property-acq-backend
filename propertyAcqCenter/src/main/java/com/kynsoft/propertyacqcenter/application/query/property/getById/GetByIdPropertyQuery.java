package com.kynsoft.propertyacqcenter.application.query.property.getById;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetByIdPropertyQuery implements IQuery {
    private String id;
}
