package com.kynsof.hospitalizationService.application.command.ubication.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateUbicationCommand implements ICommand {

    private UUID id;
    private String name;
    private String code;

    public UpdateUbicationCommand(UUID id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public static UpdateUbicationCommand fromRequest(UpdateUbicationRequest request, UUID id) {
        return new UpdateUbicationCommand(
                id,
                request.getName(),
                request.getCode()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateUbicationMessage(id);
    }
}
