package com.kynsoft.cirugia.application.query.business.getbyid;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;

import java.util.UUID;

@Getter
public class GetBusinessByIdQuery implements IQuery {
    private final UUID id;

    public GetBusinessByIdQuery(UUID id) {
        this.id = id;
    }
}