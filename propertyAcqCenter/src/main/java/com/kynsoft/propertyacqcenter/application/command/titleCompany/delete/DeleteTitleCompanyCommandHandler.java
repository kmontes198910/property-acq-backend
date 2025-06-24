package com.kynsoft.propertyacqcenter.application.command.titleCompany.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyService;
import org.springframework.beans.factory.annotation.Qualifier;

@Component
public class DeleteTitleCompanyCommandHandler implements ICommandHandler<DeleteTitleCompanyCommand> {

    private final ICompanyService companyService;

    public DeleteTitleCompanyCommandHandler(@Qualifier("titleCompanyService") ICompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    public void handle(DeleteTitleCompanyCommand command) {
        this.companyService.delete(command.getId());
    }
}
