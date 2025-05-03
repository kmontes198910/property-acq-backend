package com.kynsoft.propertyacqcenter.application.command.company.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.BusinessDto;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyDto;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyTypeDto;
import com.kynsoft.propertyacqcenter.domain.dto.SubCompanyTypeDto;
import com.kynsoft.propertyacqcenter.domain.services.IBusinessService;
import org.springframework.stereotype.Component;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyService;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyTypeService;
import com.kynsoft.propertyacqcenter.domain.services.ISubCompanyTypeService;

@Component
public class CreateCompanyCommandHandler implements ICommandHandler<CreateCompanyCommand> {

    private final ICompanyService contactPersonService;
    private final IBusinessService businessService;
    private final ICompanyTypeService companyTypeService;
    private final ISubCompanyTypeService subCompanyTypeService;

    public CreateCompanyCommandHandler(ICompanyService contactPersonService,
                                       IBusinessService businessService,
                                       ICompanyTypeService companyTypeService,
                                       ISubCompanyTypeService subCompanyTypeService) {
        this.contactPersonService = contactPersonService;
        this.businessService = businessService;
        this.companyTypeService = companyTypeService;
        this.subCompanyTypeService = subCompanyTypeService;
    }

    @Override
    public void handle(CreateCompanyCommand command) {
        BusinessDto businessDto = this.businessService.findById(command.getBusiness());
        CompanyTypeDto companyTypeDto = this.companyTypeService.findById(command.getCompanyType());
        SubCompanyTypeDto subCompanyTypeDto = this.subCompanyTypeService.findById(command.getSubCompanyType());
        CompanyDto contactPersonDto = new CompanyDto(
                command.getId(), 
                businessDto, 
                companyTypeDto, 
                subCompanyTypeDto, 
                command.getTitle(), 
                command.getOwnershipPercentage(), 
                command.getSignatureAuthority(), 
                command.getNotes(), 
                null, 
                null, 
                null, 
                null
        );

        this.contactPersonService.create(contactPersonDto);
    }
}
