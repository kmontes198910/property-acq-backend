package com.kynsof.calendar.application.command.schedule.changeResource;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ChangeResourceMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_SCHEDULE";

    public ChangeResourceMessage(UUID id) {
        this.id = id;
    }

}
