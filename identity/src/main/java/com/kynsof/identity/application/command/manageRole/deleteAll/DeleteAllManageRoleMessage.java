package com.kynsof.identity.application.command.manageRole.deleteAll;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;


@Getter
public class DeleteAllManageRoleMessage implements ICommandMessage {

    private final String command = "DELETE";

    public DeleteAllManageRoleMessage() {}

}
