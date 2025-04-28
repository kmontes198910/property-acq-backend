package com.kynsoft.cirugia.application.query.surgery.getbyid;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;

import java.util.UUID;

@Getter
public class GetSurgeryByIdQuery implements IQuery {
    private final UUID id;

    public GetSurgeryByIdQuery(UUID id) {
        this.id = id;
    }
}