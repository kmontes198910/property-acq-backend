package com.kynsoft.propertyacqcenter.application.command.teamAssignment.delete;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteTeamAssignmentCommand implements ICommand {

    private UUID id;

    @Override
    public ICommandMessage getMessage() {
        return new DeleteTeamAssignmentMessage(id);
    }
}
