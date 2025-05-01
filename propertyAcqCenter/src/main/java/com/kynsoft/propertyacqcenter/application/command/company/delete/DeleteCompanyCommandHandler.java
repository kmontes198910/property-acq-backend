package com.kynsoft.propertyacqcenter.application.command.company.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.IContactPersonService;
import org.springframework.stereotype.Component;

@Component
public class DeleteCompanyCommandHandler implements ICommandHandler<DeleteCompanyCommand> {

    private final IContactPersonService contactPersonService;

    public DeleteCompanyCommandHandler(IContactPersonService contactPersonService) {
        this.contactPersonService = contactPersonService;
    }

    @Override
    public void handle(DeleteCompanyCommand command) {
        this.contactPersonService.delete(command.getId());
    }
}
