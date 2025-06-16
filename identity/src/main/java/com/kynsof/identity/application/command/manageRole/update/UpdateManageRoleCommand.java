package com.kynsof.identity.application.command.manageRole.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateManageRoleCommand implements ICommand {

    private UUID id;
    private String code;
    private String name;

    public UpdateManageRoleCommand(UUID id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public static UpdateManageRoleCommand fromRequest(UpdateManageRoleRequest request, UUID id) {
        return new UpdateManageRoleCommand(id, request.getCode(), request.getName());
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateManageRoleMessage(id);
    }
}
