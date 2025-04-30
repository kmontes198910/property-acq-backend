package com.kynsoft.cirugia.application.query.operatingroom.listbybusiness;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.cirugia.domain.dto.OperatingRoom;
import lombok.Getter;

import java.util.List;

@Getter
public class OperatingRoomListResponse implements IResponse {
    private final List<OperatingRoom> operatingRooms;
    
    public OperatingRoomListResponse(List<OperatingRoom> operatingRooms) {
        this.operatingRooms = operatingRooms;
    }
}