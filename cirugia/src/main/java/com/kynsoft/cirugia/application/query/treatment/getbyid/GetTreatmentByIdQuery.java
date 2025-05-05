package com.kynsoft.cirugia.application.query.treatment.getbyid;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class GetTreatmentByIdQuery implements IQuery {
    private final UUID id;
    
    public GetTreatmentByIdQuery(UUID id) {
        this.id = id;
    }
}