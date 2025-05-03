package com.kynsoft.cirugia.application.command.intraOperative.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateIntraOperativeMessage implements ICommandMessage {
    private final UUID id;

    public CreateIntraOperativeMessage(UUID id) {
        this.id = id;
    }
}