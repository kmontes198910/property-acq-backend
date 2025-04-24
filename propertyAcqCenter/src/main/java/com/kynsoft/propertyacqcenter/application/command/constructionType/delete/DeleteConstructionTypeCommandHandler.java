package com.kynsoft.propertyacqcenter.application.command.constructionType.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.IConstructionTypeService;
import org.springframework.stereotype.Component;

@Component
public class DeleteConstructionTypeCommandHandler implements ICommandHandler<DeleteConstructionTypeCommand> {

    private final IConstructionTypeService constructionTypeService;

    public DeleteConstructionTypeCommandHandler(IConstructionTypeService constructionTypeService) {
        this.constructionTypeService = constructionTypeService;
    }

    @Override
    public void handle(DeleteConstructionTypeCommand command) {

        constructionTypeService.delete(command.getId());
    }

}
