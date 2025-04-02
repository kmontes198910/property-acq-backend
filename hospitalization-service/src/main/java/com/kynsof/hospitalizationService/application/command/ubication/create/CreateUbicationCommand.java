package com.kynsof.hospitalizationService.application.command.ubication.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateUbicationCommand implements ICommand {

    private UUID id;
    private String name;
    private String code;

    public CreateUbicationCommand(String name, String code) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.code = code;
    }

    public static CreateUbicationCommand fromRequest(CreateUbicationRequest request) {
        return new CreateUbicationCommand(
                request.getName(),
                request.getCode()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateUbicationMessage(id);
    }
}
