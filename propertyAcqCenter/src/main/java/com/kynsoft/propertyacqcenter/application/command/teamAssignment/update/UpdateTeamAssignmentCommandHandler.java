package com.kynsoft.propertyacqcenter.application.command.teamAssignment.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.dto.TeamAssignmentDto;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import com.kynsoft.propertyacqcenter.domain.services.ITeamAssignmentService;
import org.springframework.stereotype.Component;

@Component
public class UpdateTeamAssignmentCommandHandler implements ICommandHandler<UpdateTeamAssignmentCommand> {

    private final ITeamAssignmentService teamAssignmentService;
    private final IPropertyService propertyService;

    public UpdateTeamAssignmentCommandHandler(ITeamAssignmentService teamAssignmentService, IPropertyService propertyService) {
        this.teamAssignmentService = teamAssignmentService;
        this.propertyService = propertyService;
    }

    @Override
    public void handle(UpdateTeamAssignmentCommand command) {
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
                .seller(command.getSeller())
                .hoa(command.getHoa())
                .build());
    }
}
