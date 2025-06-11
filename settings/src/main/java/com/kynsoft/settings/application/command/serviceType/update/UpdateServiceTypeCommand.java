package com.kynsoft.settings.application.command.serviceType.update;


import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.settings.domain.enums.EServiceStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateServiceTypeCommand implements ICommand {

    private UUID id;
    private String name;
    private final String picture;
    private EServiceStatus status;

    public UpdateServiceTypeCommand(UUID id, String description, String picture, EServiceStatus status) {
        this.id = id;
        this.name = description;
        this.picture = picture;
        this.status = status;
    }

    public static UpdateServiceTypeCommand fromRequest(UUID id, UpdateServiceTypeRequest request) {
        return new UpdateServiceTypeCommand(id, request.getName(), request.getImage(), request.getStatus());
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateServiceTypeMessage(id);
    }
}
