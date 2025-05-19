package com.kynsoft.propertyacqcenter.application.command.propertyImages.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyImagesDto;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyImagesService;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import org.springframework.stereotype.Component;

@Component
public class CreatePropertyImagesCommandHandler implements ICommandHandler<CreatePropertyImagesCommand> {

    private final IPropertyImagesService imagesService;
    private final IPropertyService propertyService;

    public CreatePropertyImagesCommandHandler(IPropertyImagesService imagesService, IPropertyService propertyService) {
        this.imagesService = imagesService;
        this.propertyService = propertyService;
    }

    @Override
    public void handle(CreatePropertyImagesCommand command) {
        PropertyDto property = this.propertyService.getById(command.getProperty());
        if (command.getMain()) {
            this.imagesService.validatePropertyImagenMain(property.getId());
        }
        imagesService.create(new PropertyImagesDto(command.getId(), property, command.getUrl(), command.getMain()));
    }
}
