package com.kynsof.payment.application.query.groupPayment.getbyid;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;

import java.util.UUID;

@Getter
public class FindByIdGroupPaymentQuery implements IQuery {

    private final UUID id;

    public FindByIdGroupPaymentQuery(UUID id) {
        this.id = id;
    }

}
