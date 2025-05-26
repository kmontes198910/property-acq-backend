package com.kynsoft.propertyacqcenter.application.command.teamAssignment.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.dto.TeamAssignmentDto;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import com.kynsoft.propertyacqcenter.domain.services.ITeamAssignmentService;
import org.springframework.stereotype.Component;

@Component
public class CreateTeamAssignmentCommandHandler implements ICommandHandler<CreateTeamAssignmentCommand> {

    private final ITeamAssignmentService teamAssignmentService;
    private final IPropertyService propertyService;

    public CreateTeamAssignmentCommandHandler(ITeamAssignmentService teamAssignmentService, IPropertyService propertyService) {
        this.teamAssignmentService = teamAssignmentService;
        this.propertyService = propertyService;
    }

    @Override
    public void handle(CreateTeamAssignmentCommand command) {
        PropertyDto propertyDto = this.propertyService.getById(command.getProperty());
        teamAssignmentService.create(TeamAssignmentDto.builder()
                .id(command.getId())
                .property(propertyDto)
                .buyerEntityName(command.getBuyerEntityName())
                .buyerContactRep(command.getBuyerContactRep())
                .titleEscrowCompany(command.getTitleEscrowCompany())
                .lenderCompany(command.getLenderCompany())
                .projectManager(command.getProjectManager())
                .legalContact(command.getLegalContact())
                .build());
    }
}
