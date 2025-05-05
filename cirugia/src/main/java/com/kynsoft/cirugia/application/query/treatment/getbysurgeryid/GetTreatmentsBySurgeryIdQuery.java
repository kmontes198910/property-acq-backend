package com.kynsoft.cirugia.application.query.treatment.getbysurgeryid;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class GetTreatmentsBySurgeryIdQuery implements IQuery {
    private final UUID surgeryId;
    
    public GetTreatmentsBySurgeryIdQuery(UUID surgeryId) {
        this.surgeryId = surgeryId;
    }
}