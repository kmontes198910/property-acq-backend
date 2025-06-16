package com.kynsof.identity.application.command.manageRole.deleteAll;


import com.kynsof.identity.domain.interfaces.service.IManageRoleService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;


@Component
public class DeleteAllManageRoleCommandHandler implements ICommandHandler<DeleteAllManageRoleCommand> {

    private final IManageRoleService serviceImpl;

    public DeleteAllManageRoleCommandHandler(IManageRoleService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public void handle(DeleteAllManageRoleCommand command) {

        serviceImpl.deleteAll(command.getIds());
    }

}
