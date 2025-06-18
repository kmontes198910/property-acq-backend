package com.kynsoft.propertyacqcenter.application.command.manageRole.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateManageRoleCommand implements ICommand {

    private UUID rol;
    private List<UUID> documentTypes;

    public static UpdateManageRoleCommand fromRequest(UUID id, UpdateManageRoleRequest request) {
        return new UpdateManageRoleCommand(
                id,
                request.getDocumentTypes()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateManageRoleMessage(rol);
    }
}
