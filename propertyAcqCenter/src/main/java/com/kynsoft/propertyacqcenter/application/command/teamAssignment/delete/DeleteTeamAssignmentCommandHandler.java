package com.kynsoft.propertyacqcenter.application.command.teamAssignment.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.ITeamAssignmentService;
import org.springframework.stereotype.Component;

@Component
public class DeleteTeamAssignmentCommandHandler implements ICommandHandler<DeleteTeamAssignmentCommand> {

    private final ITeamAssignmentService teamAssignmentService;

    public DeleteTeamAssignmentCommandHandler(ITeamAssignmentService teamAssignmentService) {
        this.teamAssignmentService = teamAssignmentService;
    }

    @Override
    public void handle(DeleteTeamAssignmentCommand command) {
        this.teamAssignmentService.delete(command.getId());
    }
}
