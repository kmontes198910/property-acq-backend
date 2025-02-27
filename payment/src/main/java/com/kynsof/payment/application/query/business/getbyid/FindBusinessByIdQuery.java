package com.kynsof.payment.application.query.business.getbyid;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;

import java.util.UUID;

@Getter
public class FindBusinessByIdQuery  implements IQuery {

    private UUID id;

    public FindBusinessByIdQuery(UUID id) {
        this.id = id;
    }

}
