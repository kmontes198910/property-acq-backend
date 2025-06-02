package com.kynsoft.propertyacqcenter.application.command.companyType.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyTypeService;
import org.springframework.stereotype.Component;

@Component
public class DeleteCompanyTypeCommandHandler implements ICommandHandler<DeleteCompanyTypeCommand> {

    private final ICompanyTypeService companyTypeService;

    public DeleteCompanyTypeCommandHandler(ICompanyTypeService companyTypeService) {
        this.companyTypeService = companyTypeService;
    }

    @Override
    public void handle(DeleteCompanyTypeCommand command) {

        companyTypeService.delete(command.getId());
    }

}
