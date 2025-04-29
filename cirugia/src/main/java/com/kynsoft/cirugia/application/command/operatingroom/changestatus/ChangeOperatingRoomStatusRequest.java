package com.kynsoft.cirugia.application.command.operatingroom.changestatus;

import lombok.Data;
import java.util.UUID;

@Data
public class ChangeOperatingRoomStatusRequest {
    private UUID operatingRoomId;
    private String status;
    private UUID updatedBy;
}