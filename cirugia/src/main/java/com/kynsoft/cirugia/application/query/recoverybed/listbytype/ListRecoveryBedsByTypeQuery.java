package com.kynsoft.cirugia.application.query.recoverybed.listbytype;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;

@Getter
public class ListRecoveryBedsByTypeQuery implements IQuery {
    private final String type;
    
    public ListRecoveryBedsByTypeQuery(String type) {
        this.type = type;
    }
}