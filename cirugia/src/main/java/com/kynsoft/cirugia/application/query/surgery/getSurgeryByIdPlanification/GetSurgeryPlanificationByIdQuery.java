package com.kynsoft.cirugia.application.query.surgery.getSurgeryByIdPlanification;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;

import java.util.UUID;

@Getter
public class GetSurgeryPlanificationByIdQuery implements IQuery {
    private final UUID id;
    private final UUID userId;

    public GetSurgeryPlanificationByIdQuery(UUID id, UUID userId) {
        this.id = id;
        this.userId = userId;
    }
}