package com.kynsoft.settings.application.query.recoverybed.getbyid;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;

import java.util.UUID;

@Getter
public class GetRecoveryBedByIdQuery implements IQuery {
    private final UUID id;

    public GetRecoveryBedByIdQuery(UUID id) {
        this.id = id;
    }
}