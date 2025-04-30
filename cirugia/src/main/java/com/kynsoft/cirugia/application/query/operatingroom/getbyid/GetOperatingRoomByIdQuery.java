package com.kynsoft.cirugia.application.query.operatingroom.getbyid;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;
import java.util.UUID;

@Getter
public class GetOperatingRoomByIdQuery implements IQuery {
    private final UUID id;
    
    public GetOperatingRoomByIdQuery(UUID id) {
        this.id = id;
    }
}