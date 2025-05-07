package com.kynsoft.propertyacqcenter.application.command.propertyImages.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreatePropertyImagesCommand implements ICommand {

    private UUID id;
    private String property;
    private String url;

    public CreatePropertyImagesCommand(String property, String url) {
        this.id = UUID.randomUUID();
        this.property = property;
        this.url = url;
    }

    public static CreatePropertyImagesCommand fromRequest(CreatePropertyImagesRequest request) {
        return new CreatePropertyImagesCommand(
                request.getProperty(),
                request.getUrl()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreatePropertyImagesMessage(id);
    }
}
