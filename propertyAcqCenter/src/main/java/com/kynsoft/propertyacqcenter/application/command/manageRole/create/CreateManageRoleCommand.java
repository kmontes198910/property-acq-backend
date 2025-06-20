package com.kynsoft.propertyacqcenter.application.command.manageRole.create;


import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateManageRoleCommand implements ICommand {
    private UUID id;
    private String code;
    private String name;
    private List<UUID> documentTypes;

    public CreateManageRoleCommand(String code, String name, List<UUID> documentTypes) {
        this.id = UUID.randomUUID();
        this.code = code;
        this.name = name;
        this.documentTypes = documentTypes;
    }
    public static CreateManageRoleCommand fromRequest(CreateManageRoleRequest request) {
        return new CreateManageRoleCommand(request.getCode(), request.getName(), request.getDocumentTypes());
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateManageRoleMessage(id);
    }
}
