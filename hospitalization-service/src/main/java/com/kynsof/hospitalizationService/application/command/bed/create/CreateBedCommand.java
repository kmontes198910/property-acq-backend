package com.kynsof.hospitalizationService.application.command.bed.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateBedCommand implements ICommand {

    private UUID id;
    private UUID ubication;
    private String code;
    private String name;

    public CreateBedCommand(UUID ubication, String code, String name) {
        this.id = UUID.randomUUID();
        this.ubication = ubication;
        this.code = code;
        this.name = name;
    }

    public static CreateBedCommand fromRequest(CreateBedRequest request) {
        return new CreateBedCommand(
                request.getUbication(),
                request.getCode(),
                request.getName()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateBedMessage(id);
    }
}
