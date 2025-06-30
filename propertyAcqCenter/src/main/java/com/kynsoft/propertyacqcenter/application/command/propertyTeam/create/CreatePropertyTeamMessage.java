package com.kynsoft.propertyacqcenter.application.command.propertyTeam.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreatePropertyTeamMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_PROPERTY_TEAM";

    public CreatePropertyTeamMessage(UUID id) {
        this.id = id;
    }

}
