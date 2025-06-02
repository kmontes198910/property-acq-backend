package com.kynsoft.propertyacqcenter.application.command.teamAssignment.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteTeamAssignmentMessage implements ICommandMessage {
    private final String command = "DELETE_TEAM_ASSIGNMENT";

    private final UUID id;

}
