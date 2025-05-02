package com.kynsoft.propertyacqcenter.application.command.subCompanyType.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;
import com.kynsoft.propertyacqcenter.domain.services.ISubCompanyTypeService;

@Component
public class DeleteSubCompanyTypeCommandHandler implements ICommandHandler<DeleteSubCompanyTypeCommand> {

    private final ISubCompanyTypeService constructionTypeService;

    public DeleteSubCompanyTypeCommandHandler(ISubCompanyTypeService constructionTypeService) {
        this.constructionTypeService = constructionTypeService;
    }

    @Override
    public void handle(DeleteSubCompanyTypeCommand command) {

        constructionTypeService.delete(command.getId());
    }

}
