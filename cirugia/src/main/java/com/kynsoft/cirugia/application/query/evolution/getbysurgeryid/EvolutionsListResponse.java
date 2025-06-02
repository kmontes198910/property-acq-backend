package com.kynsoft.cirugia.application.query.evolution.getbysurgeryid;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.cirugia.domain.dto.Evolution;
import lombok.Getter;

import java.util.List;

@Getter
public class EvolutionsListResponse implements IResponse {
    private final List<Evolution> evolutions;
    
    public EvolutionsListResponse(List<Evolution> evolutions) {
        this.evolutions = evolutions;
    }
}