package com.kynsoft.propertyacqcenter.application.command.teamAssignment.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateTeamAssignmentMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_TEAM_ASSIGNMENT";

    public CreateTeamAssignmentMessage(UUID id) {
        this.id = id;
    }

}
