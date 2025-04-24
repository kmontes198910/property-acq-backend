package com.kynsoft.propertyacqcenter.application.command.constructionType.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.ConstructionTypeDto;
import com.kynsoft.propertyacqcenter.domain.services.IConstructionTypeService;
import org.springframework.stereotype.Component;

@Component
public class CreateConstructionTypeCommandHandler implements ICommandHandler<CreateConstructionTypeCommand> {

    private final IConstructionTypeService constructionTypeService;

    public CreateConstructionTypeCommandHandler(IConstructionTypeService constructionTypeService) {
        this.constructionTypeService = constructionTypeService;
    }

    @Override
    public void handle(CreateConstructionTypeCommand command) {
        constructionTypeService.create(new ConstructionTypeDto(
                command.getId(), 
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
