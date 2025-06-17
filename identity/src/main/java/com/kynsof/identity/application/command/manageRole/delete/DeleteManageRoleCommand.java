package com.kynsof.identity.application.command.manageRole.delete;

import com.kynsof.identity.application.command.permission.delete.DeletePermissionMessage;
import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
public class DeleteManageRoleCommand implements ICommand {

    private UUID id;

    @Override
    public ICommandMessage getMessage() {
        return new DeleteManageRoleMessage(id);
    }

}
