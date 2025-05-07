package com.kynsoft.propertyacqcenter.application.command.propertyImages.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdatePropertyImagesCommand implements ICommand {

    private UUID id;
    private String property;
    private String url;

    public UpdatePropertyImagesCommand(UUID id, String property, String url) {
        this.id = id;
        this.property = property;
        this.url = url;
    }

    public static UpdatePropertyImagesCommand fromRequest(UpdatePropertyImagesRequest request, UUID id) {
        return new UpdatePropertyImagesCommand(
                id,
                request.getProperty(),
                request.getUrl()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdatePropertyImagesMessage(id);
    }
}
