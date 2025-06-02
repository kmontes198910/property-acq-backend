package com.kynsoft.propertyacqcenter.application.command.subCompanyType.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyTypeDto;
import com.kynsoft.propertyacqcenter.domain.dto.SubCompanyTypeDto;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyTypeService;
import org.springframework.stereotype.Component;
import com.kynsoft.propertyacqcenter.domain.services.ISubCompanyTypeService;

@Component
public class CreateSubCompanyTypeCommandHandler implements ICommandHandler<CreateSubCompanyTypeCommand> {

    private final ISubCompanyTypeService subCompanyTypeService;
    private final ICompanyTypeService companyTypeService;

    public CreateSubCompanyTypeCommandHandler(ISubCompanyTypeService subCompanyTypeService, ICompanyTypeService companyTypeService) {
        this.subCompanyTypeService = subCompanyTypeService;
        this.companyTypeService = companyTypeService;
    }

    @Override
    public void handle(CreateSubCompanyTypeCommand command) {
        CompanyTypeDto companyTypeDto = this.companyTypeService.findById(command.getCompanyType());
        subCompanyTypeService.create(new SubCompanyTypeDto(
                command.getId(), 
                companyTypeDto,
                command.getName(), 
                command.getDescription(), 
                command.getCode(), 
                command.getIsSpecialized(), 
                command.getSpecializationArea(), 
                command.getRequiresLicense(), 
                command.getIsActive()
        ));
    }
}
