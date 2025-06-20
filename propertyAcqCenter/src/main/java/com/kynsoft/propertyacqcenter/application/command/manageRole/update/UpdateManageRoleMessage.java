package com.kynsoft.propertyacqcenter.application.command.manageRole.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateManageRoleMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_ROLE";

    public UpdateManageRoleMessage(UUID id) {
        this.id = id;
    }

}
