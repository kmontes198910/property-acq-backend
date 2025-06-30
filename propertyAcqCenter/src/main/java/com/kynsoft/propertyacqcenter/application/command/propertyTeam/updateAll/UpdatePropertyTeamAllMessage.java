package com.kynsoft.propertyacqcenter.application.command.propertyTeam.updateAll;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdatePropertyTeamAllMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_PROPERTY_TEAM";

    public UpdatePropertyTeamAllMessage(UUID id) {
        this.id = id;
    }

}
