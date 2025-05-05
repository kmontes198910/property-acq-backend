package com.kynsoft.propertyacqcenter.application.command.companyContact.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyContactService;
import org.springframework.stereotype.Component;

@Component
public class DeleteCompanyContactCommandHandler implements ICommandHandler<DeleteCompanyContactCommand> {

    private final ICompanyContactService companyContactService;

    public DeleteCompanyContactCommandHandler(ICompanyContactService companyContactService) {
        this.companyContactService = companyContactService;
    }

    @Override
    public void handle(DeleteCompanyContactCommand command) {

        companyContactService.delete(command.getId());
    }

}
