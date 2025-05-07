package com.kynsoft.propertyacqcenter.application.command.propertyImages.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyImagesDto;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyImagesService;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import org.springframework.stereotype.Component;

@Component
public class UpdatePropertyImagesCommandHandler implements ICommandHandler<UpdatePropertyImagesCommand> {

    private final IPropertyImagesService imagesService;
    private final IPropertyService propertyService;

    public UpdatePropertyImagesCommandHandler(IPropertyImagesService imagesService, IPropertyService propertyService) {
        this.imagesService = imagesService;
        this.propertyService = propertyService;
    }

    @Override
    public void handle(UpdatePropertyImagesCommand command) {
        PropertyDto property = this.propertyService.getById(command.getProperty());
        imagesService.update(new PropertyImagesDto(command.getId(), property, command.getUrl()));
    }
}
