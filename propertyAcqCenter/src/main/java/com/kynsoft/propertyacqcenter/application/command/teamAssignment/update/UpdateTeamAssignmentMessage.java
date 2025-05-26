package com.kynsoft.propertyacqcenter.application.command.teamAssignment.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateTeamAssignmentMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_TEAM_ASSIGNMENT";

    public UpdateTeamAssignmentMessage(UUID id) {
        this.id = id;
    }

}
