package com.kynsof.hospitalizationService.application.command.bed.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateBedCommand implements ICommand {

    private UUID id;
    private UUID ubication;
    private String code;
    private String name;

    public UpdateBedCommand(UUID id, UUID ubication, String code, String name) {
        this.id = id;
        this.ubication = ubication;
        this.code = code;
        this.name = name;
    }

    public static UpdateBedCommand fromRequest(UpdateBedRequest request, UUID id) {
        return new UpdateBedCommand(
                id,
                request.getUbication(),
                request.getCode(),
                request.getName()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateBedMessage(id);
    }
}
