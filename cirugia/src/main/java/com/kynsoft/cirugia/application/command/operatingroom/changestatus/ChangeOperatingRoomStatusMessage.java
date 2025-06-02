package com.kynsoft.cirugia.application.command.operatingroom.changestatus;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ChangeOperatingRoomStatusMessage implements ICommandMessage {
    private final UUID id;

    public ChangeOperatingRoomStatusMessage(UUID id) {
        this.id = id;
    }
}