package com.kynsoft.propertyacqcenter.application.command.constructionType.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.ConstructionTypeDto;
import com.kynsoft.propertyacqcenter.domain.services.IConstructionTypeService;
import org.springframework.stereotype.Component;

@Component
public class UpdateConstructionTypeCommandHandler implements ICommandHandler<UpdateConstructionTypeCommand> {

    private final IConstructionTypeService constructionTypeService;

    public UpdateConstructionTypeCommandHandler(IConstructionTypeService constructionTypeService) {
        this.constructionTypeService = constructionTypeService;
    }

    @Override
    public void handle(UpdateConstructionTypeCommand command) {
        constructionTypeService.update(new ConstructionTypeDto(
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
