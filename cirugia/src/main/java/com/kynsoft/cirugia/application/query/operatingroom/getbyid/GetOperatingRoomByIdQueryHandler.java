package com.kynsoft.cirugia.application.query.operatingroom.getbyid;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.cirugia.domain.service.IOperatingRoomService;
import com.kynsoft.cirugia.domain.dto.OperatingRoom;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetOperatingRoomByIdQueryHandler implements IQueryHandler<GetOperatingRoomByIdQuery, OperatingRoomResponse> {

    private final IOperatingRoomService service;

    public GetOperatingRoomByIdQueryHandler(IOperatingRoomService service) {
        this.service = service;
    }

    @Override
    public OperatingRoomResponse handle(GetOperatingRoomByIdQuery query) {
        Optional<OperatingRoom> operatingRoom = service.getOperatingRoomById(query.getId());
        
        if (operatingRoom.isEmpty()) {
            throw new RuntimeException("Operating Room not found with ID: " + query.getId());
        }
        
        return new OperatingRoomResponse(operatingRoom.get());
    }
}