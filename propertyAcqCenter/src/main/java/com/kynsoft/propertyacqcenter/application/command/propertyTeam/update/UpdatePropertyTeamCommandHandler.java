package com.kynsoft.propertyacqcenter.application.command.propertyTeam.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyContactDto;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyTeamDto;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyContactService;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyTeamService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UpdatePropertyTeamCommandHandler implements ICommandHandler<UpdatePropertyTeamCommand> {

    private final IPropertyTeamService propertyTeamService;
    private final IPropertyService propertyService;
    private final ICompanyContactService companyContactService;

    public UpdatePropertyTeamCommandHandler(IPropertyTeamService propertyTeamService,
            IPropertyService propertyService, ICompanyContactService companyContactService) {
        this.propertyTeamService = propertyTeamService;
        this.propertyService = propertyService;
        this.companyContactService = companyContactService;
    }

    @Override
    @Transactional
    public void handle(UpdatePropertyTeamCommand command) {
        CompanyContactDto contact = this.companyContactService.findById(command.getContact());
        PropertyDto propertyDto = this.propertyService.getById(command.getProperty());

        this.propertyTeamService.update(PropertyTeamDto
                .builder()
                .id(command.getId())
                .contact(contact)
                .property(propertyDto)
                .profile(command.getPerfil())
                .build()
        );
    }

}
