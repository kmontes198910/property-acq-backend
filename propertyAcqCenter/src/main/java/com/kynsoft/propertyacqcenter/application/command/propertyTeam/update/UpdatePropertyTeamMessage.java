package com.kynsoft.propertyacqcenter.application.command.propertyTeam.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdatePropertyTeamMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_PROPERTY_TEAM";

    public UpdatePropertyTeamMessage(UUID id) {
        this.id = id;
    }

}
