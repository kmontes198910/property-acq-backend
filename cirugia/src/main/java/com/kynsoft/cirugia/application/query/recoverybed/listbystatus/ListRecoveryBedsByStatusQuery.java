package com.kynsoft.cirugia.application.query.recoverybed.listbystatus;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;

@Getter
public class ListRecoveryBedsByStatusQuery implements IQuery {
    private final String status;
    
    public ListRecoveryBedsByStatusQuery(String status) {
        this.status = status;
    }
}