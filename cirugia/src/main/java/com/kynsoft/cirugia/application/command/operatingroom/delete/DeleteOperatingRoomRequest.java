package com.kynsoft.cirugia.application.command.operatingroom.delete;

import lombok.Data;
import java.util.UUID;

@Data
public class DeleteOperatingRoomRequest {
    private UUID operatingRoomId;
}