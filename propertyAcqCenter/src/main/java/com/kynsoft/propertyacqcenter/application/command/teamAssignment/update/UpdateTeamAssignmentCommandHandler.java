package com.kynsoft.propertyacqcenter.application.command.teamAssignment.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyContactDto;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.dto.TeamAssignmentDto;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyContactService;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import com.kynsoft.propertyacqcenter.domain.services.ITeamAssignmentService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class UpdateTeamAssignmentCommandHandler implements ICommandHandler<UpdateTeamAssignmentCommand> {

    private final ITeamAssignmentService teamAssignmentService;
    private final IPropertyService propertyService;
    private final ICompanyContactService companyContactService;
    private final ILegalEntityService legalEntityService;

    public UpdateTeamAssignmentCommandHandler(ITeamAssignmentService teamAssignmentService,
            IPropertyService propertyService, ICompanyContactService companyContactService,
            ILegalEntityService legalEntityService) {
        this.teamAssignmentService = teamAssignmentService;
        this.propertyService = propertyService;
        this.companyContactService = companyContactService;
        this.legalEntityService = legalEntityService;
    }

    @Override
    public void handle(UpdateTeamAssignmentCommand command) {
        PropertyDto propertyDto = this.propertyService.getById(command.getProperty());
        LegalEntityDto buyerEntityName = command.getBuyerEntityName() != null ? this.legalEntityService.findById(command.getBuyerEntityName()) : null;
        teamAssignmentService.create(TeamAssignmentDto.builder()
                .id(command.getId())
                .property(propertyDto)
                .buyerEntityName(buyerEntityName)
                .buyerContactReps(this.get(command.getBuyerContactRep()))
                .titleEscrowCompany(this.get(command.getTitleEscrowCompany()))
                .lenderCompany(this.get(command.getLenderCompany()))
                .projectManager(this.get(command.getProjectManager()))
                .legalContact(this.get(command.getLegalContact()))
                .seller(this.get(command.getSeller()))
                .hoa(this.get(command.getHoa()))
                .build());
    }

    private List<CompanyContactDto> get(List<UUID> ids) {
        return ids.stream()
                .map(this.companyContactService::findById)
                .collect(Collectors.toList());
    }

}
