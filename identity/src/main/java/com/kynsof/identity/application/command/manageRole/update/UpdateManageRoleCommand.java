package com.kynsof.identity.application.command.manageRole.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateManageRoleCommand implements ICommand {

    private UUID id;
    private String code;
    private String name;
    private List<UUID> permissions;

    public UpdateManageRoleCommand(UUID id, String code, String name, List<UUID> permissions) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.permissions = permissions;
    }

    public static UpdateManageRoleCommand fromRequest(UpdateManageRoleRequest request, UUID id) {
        return new UpdateManageRoleCommand(id, request.getCode(), request.getName(), request.getPermissions());
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateManageRoleMessage(id);
    }
}
