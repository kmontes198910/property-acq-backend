package com.kynsoft.propertyacqcenter.application.command.manageRole.updateDocumentType;

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
public class UpdateManageRoleDocumentTypeCommand implements ICommand {

    private UUID rol;
    private List<UUID> documentTypes;

    public static UpdateManageRoleDocumentTypeCommand fromRequest(UUID id, UpdateManageRoleDocumentTypeRequest request) {
        return new UpdateManageRoleDocumentTypeCommand(
                id,
                request.getDocumentTypes()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateManageRoleDocumentTypeMessage(rol);
    }
}
