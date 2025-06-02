package com.kynsoft.cirugia.application.command.surgery.changestatus;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ChangeSurgeryStatusMessage implements ICommandMessage {
    private final UUID id;

    public ChangeSurgeryStatusMessage(UUID id) {
        this.id = id;
    }
}