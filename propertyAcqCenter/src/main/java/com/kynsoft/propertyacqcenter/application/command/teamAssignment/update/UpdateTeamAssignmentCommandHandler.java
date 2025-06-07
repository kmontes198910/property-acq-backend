package com.kynsoft.propertyacqcenter.application.command.teamAssignment.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyContactDto;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.dto.TeamAssignmentDto;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyContactService;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import com.kynsoft.propertyacqcenter.domain.services.ITeamAssignmentService;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class UpdateTeamAssignmentCommandHandler implements ICommandHandler<UpdateTeamAssignmentCommand> {

    private final ITeamAssignmentService teamAssignmentService;
    private final IPropertyService propertyService;
    private final ICompanyContactService companyContactService;

    public UpdateTeamAssignmentCommandHandler(ITeamAssignmentService teamAssignmentService, IPropertyService propertyService, ICompanyContactService companyContactService) {
        this.teamAssignmentService = teamAssignmentService;
        this.propertyService = propertyService;
        this.companyContactService = companyContactService;
    }

    @Override
    public void handle(UpdateTeamAssignmentCommand command) {
        PropertyDto propertyDto = this.propertyService.getById(command.getProperty());
        CompanyContactDto buyerEntityName = command.getBuyerEntityName() != null ? this.companyContactService.findById(UUID.fromString(command.getBuyerEntityName())) : null;
        CompanyContactDto buyerContactRep = command.getBuyerContactRep() != null ? this.companyContactService.findById(UUID.fromString(command.getBuyerContactRep())) : null;
        CompanyContactDto titleEscrowCompany = command.getTitleEscrowCompany() != null ? this.companyContactService.findById(UUID.fromString(command.getTitleEscrowCompany())) : null;
        CompanyContactDto lenderCompany = command.getLenderCompany() != null ? this.companyContactService.findById(UUID.fromString(command.getLenderCompany())) : null;
        CompanyContactDto projectManager = command.getProjectManager() != null ? this.companyContactService.findById(UUID.fromString(command.getProjectManager())) : null;
        CompanyContactDto legalContact = command.getLegalContact() != null ? this.companyContactService.findById(UUID.fromString(command.getLegalContact())) : null;
        CompanyContactDto seller = command.getSeller() != null ? this.companyContactService.findById(UUID.fromString(command.getSeller())) : null;
        CompanyContactDto hoa = command.getHoa() != null ? this.companyContactService.findById(UUID.fromString(command.getHoa())) : null;
        teamAssignmentService.create(TeamAssignmentDto.builder()
                .id(command.getId())
                .property(propertyDto)
                .buyerEntityName(buyerEntityName)
                .buyerContactRep(buyerContactRep)
                .titleEscrowCompany(titleEscrowCompany)
                .lenderCompany(lenderCompany)
                .projectManager(projectManager)
                .legalContact(legalContact)
                .seller(seller)
                .hoa(hoa)
                .build());
    }
}
