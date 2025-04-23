package com.kynsoft.propertyacqcenter.application.command.companyType.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyTypeDto;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyTypeService;
import org.springframework.stereotype.Component;

@Component
public class UpdateCompanyTypeCommandHandler implements ICommandHandler<UpdateCompanyTypeCommand> {

    private final ICompanyTypeService companyTypeService;

    public UpdateCompanyTypeCommandHandler(ICompanyTypeService companyTypeService) {
        this.companyTypeService = companyTypeService;
    }

    @Override
    public void handle(UpdateCompanyTypeCommand command) {
        companyTypeService.update(new CompanyTypeDto(
                command.getId(), 
                command.getName(), 
                command.getCode(), 
                command.getDescription(), 
                command.getExamples(), 
                command.getIsActive()
        ));
    }
}
