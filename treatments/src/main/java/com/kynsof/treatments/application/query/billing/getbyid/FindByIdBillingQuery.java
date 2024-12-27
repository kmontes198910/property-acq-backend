package com.kynsof.treatments.application.query.billing.getbyid;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;

import java.util.UUID;

@Getter
public class FindByIdBillingQuery implements IQuery {

    private final UUID id;

    public FindByIdBillingQuery(UUID id) {
        this.id = id;
    }

}
