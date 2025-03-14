package com.kynsof.payment.application.query.accountReconciliation.getById;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;

import java.util.UUID;

@Getter
public class FindByIdAccountReconciliationQuery implements IQuery {

    private final UUID id;

    public FindByIdAccountReconciliationQuery(UUID id) {
        this.id = id;
    }

}
