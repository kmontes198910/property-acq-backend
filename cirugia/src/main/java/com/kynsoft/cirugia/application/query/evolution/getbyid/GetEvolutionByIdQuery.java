package com.kynsoft.cirugia.application.query.evolution.getbyid;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;

import java.util.UUID;

@Getter
public class GetEvolutionByIdQuery implements IQuery {
    private final UUID id;
    
    public GetEvolutionByIdQuery(UUID id) {
        this.id = id;
    }
}