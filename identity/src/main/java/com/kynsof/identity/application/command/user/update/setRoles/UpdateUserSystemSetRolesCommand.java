package com.kynsof.identity.application.command.user.update.setRoles;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateUserSystemSetRolesCommand implements ICommand {
    private UUID id;
    private List<UUID> roles;

    public UpdateUserSystemSetRolesCommand(UUID id, List<UUID> roles) {
        this.id = id;
        this.roles = roles;
    }

    public static UpdateUserSystemSetRolesCommand fromRequest(UUID id, UpdateUserSystemSetRolesRequest request) {
        return new UpdateUserSystemSetRolesCommand(
                id,
                request.getRoles()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateUserSystemSetRolesMessage();
    }
}
