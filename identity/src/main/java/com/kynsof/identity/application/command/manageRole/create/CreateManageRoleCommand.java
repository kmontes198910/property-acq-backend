package com.kynsof.identity.application.command.manageRole.create;


import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateManageRoleCommand implements ICommand {
    private UUID id;
    private String code;
    private String name;

    public CreateManageRoleCommand( String code, String name) {
        this.id = UUID.randomUUID();
        this.code = code;
        this.name = name;
    }
    public static CreateManageRoleCommand fromRequest(CreateManageRoleRequest request) {
        return new CreateManageRoleCommand(request.getCode(), request.getName());
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateManageRoleMessage(id);
    }
}
