package com.kynsoft.propertyacqcenter.application.command.business.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateBusinessCommand implements ICommand {

    private UUID id;
    private String name;

    public UpdateBusinessCommand(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public static UpdateBusinessCommand fromRequest(UpdateBusinessRequest request, UUID id) {
        return new UpdateBusinessCommand(
                id,
                request.getName()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateBusinessMessage(id);
    }
}
