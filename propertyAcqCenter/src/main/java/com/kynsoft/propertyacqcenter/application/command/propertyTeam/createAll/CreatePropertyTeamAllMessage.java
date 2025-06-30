package com.kynsoft.propertyacqcenter.application.command.propertyTeam.createAll;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreatePropertyTeamAllMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_PROPERTY_TEAM";

    public CreatePropertyTeamAllMessage(UUID id) {
        this.id = id;
    }

}
