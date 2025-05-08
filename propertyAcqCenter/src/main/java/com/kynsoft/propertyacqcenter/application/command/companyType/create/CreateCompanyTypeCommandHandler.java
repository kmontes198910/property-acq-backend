package com.kynsoft.propertyacqcenter.application.command.companyType.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyTypeDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.companyType.CompanyTypeCodeMustBeUniqueException;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyTypeService;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class CreateCompanyTypeCommandHandler implements ICommandHandler<CreateCompanyTypeCommand> {

    private final ICompanyTypeService companyTypeService;

    public CreateCompanyTypeCommandHandler(ICompanyTypeService companyTypeService) {
        this.companyTypeService = companyTypeService;
    }

    @Override
    public void handle(CreateCompanyTypeCommand command) {
        this.validateCode(command.getCode());
        companyTypeService.create(new CompanyTypeDto(
                command.getId(), 
                command.getName(), 
                command.getCode(), 
                command.getDescription(), 
                command.getExamples(), 
                command.getIsActive(), 
                LocalDateTime.now(), 
                LocalDateTime.now()
        ));
    }

    private void validateCode(String code) {
        if (this.companyTypeService.countByCode(code) > 0) {
            throw new CompanyTypeCodeMustBeUniqueException(code);
        }
    }

}
