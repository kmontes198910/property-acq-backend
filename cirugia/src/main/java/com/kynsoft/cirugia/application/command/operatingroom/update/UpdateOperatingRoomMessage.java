package com.kynsoft.cirugia.application.command.operatingroom.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateOperatingRoomMessage implements ICommandMessage {
    private final UUID id;

    public UpdateOperatingRoomMessage(UUID id) {
        this.id = id;
    }
}