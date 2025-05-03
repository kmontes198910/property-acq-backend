package com.kynsoft.cirugia.application.query.diagnosis.getbyid;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;

import java.util.UUID;

@Getter
public class GetDiagnosisByIdQuery implements IQuery {
    private final UUID id;

    public GetDiagnosisByIdQuery(UUID id) {
        this.id = id;
    }
}