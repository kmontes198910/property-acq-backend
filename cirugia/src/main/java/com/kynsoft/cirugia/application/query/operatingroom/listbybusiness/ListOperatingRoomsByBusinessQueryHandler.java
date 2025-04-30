package com.kynsoft.cirugia.application.query.operatingroom.listbybusiness;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.cirugia.application.query.operatingroom.OperatingRoomSearchResponse;
import com.kynsoft.cirugia.domain.service.IOperatingRoomService;
import com.kynsoft.cirugia.domain.dto.OperatingRoom;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListOperatingRoomsByBusinessQueryHandler implements IQueryHandler<ListOperatingRoomsByBusinessQuery, OperatingRoomListResponse> {

    private final IOperatingRoomService service;

    public ListOperatingRoomsByBusinessQueryHandler(IOperatingRoomService service) {
        this.service = service;
    }

    @Override
    public OperatingRoomListResponse handle(ListOperatingRoomsByBusinessQuery query) {
        List<OperatingRoom> operatingRooms = service.listOperatingRoomsByBusiness(query.getBusinessId());
        return new OperatingRoomListResponse(operatingRooms);
    }
}