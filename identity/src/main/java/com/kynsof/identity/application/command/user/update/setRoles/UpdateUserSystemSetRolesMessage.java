package com.kynsof.identity.application.command.user.update.setRoles;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

@Getter
public class UpdateUserSystemSetRolesMessage implements ICommandMessage {

    private final String command = "UPDATE_USER_SYSTEM";

    public UpdateUserSystemSetRolesMessage() {

    }

}
