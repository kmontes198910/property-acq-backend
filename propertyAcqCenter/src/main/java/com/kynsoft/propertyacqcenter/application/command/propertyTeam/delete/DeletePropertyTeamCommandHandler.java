package com.kynsoft.propertyacqcenter.application.command.propertyTeam.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyTeamService;
import org.springframework.stereotype.Component;

@Component
public class DeletePropertyTeamCommandHandler implements ICommandHandler<DeletePropertyTeamCommand> {

    private final IPropertyTeamService propertyTeamService;

    public DeletePropertyTeamCommandHandler(IPropertyTeamService propertyTeamService) {
        this.propertyTeamService = propertyTeamService;
    }

    @Override
    public void handle(DeletePropertyTeamCommand command) {
        this.propertyTeamService.delete(command.getId());
    }
}
