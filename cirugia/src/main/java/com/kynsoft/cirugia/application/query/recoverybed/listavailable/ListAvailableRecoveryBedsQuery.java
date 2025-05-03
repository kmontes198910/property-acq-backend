package com.kynsoft.cirugia.application.query.recoverybed.listavailable;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ListAvailableRecoveryBedsQuery implements IQuery {
    private final UUID businessId;
    
    public ListAvailableRecoveryBedsQuery(UUID businessId) {
        this.businessId = businessId;
    }
}