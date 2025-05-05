package com.kynsoft.cirugia.application.query.evolution.getbysurgeryid;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;

import java.util.UUID;

@Getter
public class GetEvolutionsBySurgeryIdQuery implements IQuery {
    private final UUID surgeryId;
    
    public GetEvolutionsBySurgeryIdQuery(UUID surgeryId) {
        this.surgeryId = surgeryId;
    }
}