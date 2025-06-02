package com.kynsoft.propertyacqcenter.application.command.company.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyService;

@Component
public class DeleteCompanyCommandHandler implements ICommandHandler<DeleteCompanyCommand> {

    private final ICompanyService contactPersonService;

    public DeleteCompanyCommandHandler(ICompanyService contactPersonService) {
        this.contactPersonService = contactPersonService;
    }

    @Override
    public void handle(DeleteCompanyCommand command) {
        this.contactPersonService.delete(command.getId());
    }
}
