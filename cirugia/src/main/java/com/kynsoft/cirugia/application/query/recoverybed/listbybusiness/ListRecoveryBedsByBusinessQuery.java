package com.kynsoft.cirugia.application.query.recoverybed.listbybusiness;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ListRecoveryBedsByBusinessQuery implements IQuery {
    private final UUID businessId;
    
    public ListRecoveryBedsByBusinessQuery(UUID businessId) {
        this.businessId = businessId;
    }
}