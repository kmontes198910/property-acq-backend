package com.kynsoft.cirugia.application.query.recoverybed.listbybusiness;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.cirugia.domain.dto.RecoveryBed;
import lombok.Getter;

import java.util.List;

@Getter
public class RecoveryBedListResponse implements IResponse {
    private final List<RecoveryBed> recoveryBeds;
    
    public RecoveryBedListResponse(List<RecoveryBed> recoveryBeds) {
        this.recoveryBeds = recoveryBeds;
    }
}