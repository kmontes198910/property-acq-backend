package com.kynsof.identity.application.command.manageRole.delete;


import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteManageRoleMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "DELETE_ROLE";

    public DeleteManageRoleMessage(UUID id) {
        this.id = id;
    }

}
