package com.kynsoft.settings.application.query.serviceType.getbyid;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;

import java.util.UUID;

@Getter
public class FindServiceTypeByIdQuery implements IQuery {

    private UUID id;

    public FindServiceTypeByIdQuery(UUID id) {
        this.id = id;
    }

}
