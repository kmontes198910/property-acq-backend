package com.kynsof.identity.application.command.manageRole.create;


import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateManageRoleMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_ROLE";

    public CreateManageRoleMessage(UUID id) {
        this.id = id;
    }

}
