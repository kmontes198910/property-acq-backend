package com.kynsoft.propertyacqcenter.application.command.propertyImages.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyImagesService;
import org.springframework.stereotype.Component;

@Component
public class DeletePropertyImagesCommandHandler implements ICommandHandler<DeletePropertyImagesCommand> {

    private final IPropertyImagesService propertyImagesService;

    public DeletePropertyImagesCommandHandler(IPropertyImagesService propertyImagesService) {
        this.propertyImagesService = propertyImagesService;
    }

    @Override
    public void handle(DeletePropertyImagesCommand command) {
        this.propertyImagesService.delete(command.getId());
    }
}
