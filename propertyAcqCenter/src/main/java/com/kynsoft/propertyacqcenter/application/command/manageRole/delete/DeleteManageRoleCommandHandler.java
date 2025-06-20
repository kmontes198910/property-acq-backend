package com.kynsoft.propertyacqcenter.application.command.manageRole.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.IManageRoleService;
import org.springframework.stereotype.Component;

@Component
public class DeleteManageRoleCommandHandler implements ICommandHandler<DeleteManageRoleCommand> {

    private final IManageRoleService service;

    public DeleteManageRoleCommandHandler(IManageRoleService service) {
        this.service = service;
    }

    @Override
    public void handle(DeleteManageRoleCommand command) {
        this.service.delete(command.getId());
    }
}
