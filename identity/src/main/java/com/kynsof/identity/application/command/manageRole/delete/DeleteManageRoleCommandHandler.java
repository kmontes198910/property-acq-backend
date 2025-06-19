package com.kynsof.identity.application.command.manageRole.delete;

import com.kynsof.identity.domain.dto.ManageRolDto;
import com.kynsof.identity.domain.interfaces.service.IManageRoleService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class DeleteManageRoleCommandHandler implements ICommandHandler<DeleteManageRoleCommand> {

    private final IManageRoleService service;

    public DeleteManageRoleCommandHandler(IManageRoleService service) {
        this.service = service;
    }

    @Override
    public void handle(DeleteManageRoleCommand command) {
        ManageRolDto delete = this.service.findById(command.getId());
//
//        delete.setIsDeleted(true);
//        delete.setCode(delete.getCode() + "-" + UUID.randomUUID());

        service.delete(delete);
    }

}