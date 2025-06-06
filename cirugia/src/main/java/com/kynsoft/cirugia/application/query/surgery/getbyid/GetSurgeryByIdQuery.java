package com.kynsoft.cirugia.application.query.surgery.getbyid;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;

import java.util.UUID;

@Getter
public class GetSurgeryByIdQuery implements IQuery {
    private final UUID id;
    private final UUID userId;

    public GetSurgeryByIdQuery(UUID id, UUID userId) {
        this.id = id;
        this.userId = userId;
    }
}