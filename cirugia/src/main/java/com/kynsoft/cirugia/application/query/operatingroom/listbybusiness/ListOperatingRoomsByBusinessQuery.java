package com.kynsoft.cirugia.application.query.operatingroom.listbybusiness;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;
import java.util.UUID;

@Getter
public class ListOperatingRoomsByBusinessQuery implements IQuery {
    private final UUID businessId;
    
    public ListOperatingRoomsByBusinessQuery(UUID businessId) {
        this.businessId = businessId;
    }
}