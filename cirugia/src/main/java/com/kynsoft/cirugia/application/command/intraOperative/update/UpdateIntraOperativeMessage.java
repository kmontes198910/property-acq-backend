package com.kynsoft.cirugia.application.command.intraOperative.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateIntraOperativeMessage implements ICommandMessage {
    private final UUID id;

    public UpdateIntraOperativeMessage(UUID id) {
        this.id = id;
    }
}