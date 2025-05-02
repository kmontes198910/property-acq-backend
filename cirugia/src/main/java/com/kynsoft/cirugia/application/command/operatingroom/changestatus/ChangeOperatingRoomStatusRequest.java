package com.kynsoft.cirugia.application.command.operatingroom.changestatus;

import com.kynsoft.cirugia.domain.enums.OperatingRoomStatus;
import lombok.Data;
import java.util.UUID;

@Data
public class ChangeOperatingRoomStatusRequest {
    private UUID operatingRoomId;
    private OperatingRoomStatus status;
    private UUID updatedBy;
}