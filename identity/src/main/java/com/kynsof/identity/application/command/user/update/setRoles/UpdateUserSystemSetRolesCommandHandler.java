package com.kynsof.identity.application.command.user.update.setRoles;

import com.kynsof.identity.controller.exception.ManageRole.UserSystemSetRolesDto;
import com.kynsof.identity.domain.dto.ManageRolDto;
import com.kynsof.identity.domain.interfaces.service.IManageRoleService;
import com.kynsof.identity.domain.interfaces.service.IUserSystemService;
import com.kynsof.identity.domain.interfaces.service.IUsersRolesService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class UpdateUserSystemSetRolesCommandHandler implements ICommandHandler<UpdateUserSystemSetRolesCommand> {

    private final IUserSystemService systemService;
    private final IManageRoleService roleService;
    private final IUsersRolesService usersRolesService;

    public UpdateUserSystemSetRolesCommandHandler(IUserSystemService systemService, IManageRoleService roleService, IUsersRolesService usersRolesService) {
        this.systemService = systemService;
        this.roleService = roleService;
        this.usersRolesService = usersRolesService;
    }

    @Override
    public void handle(UpdateUserSystemSetRolesCommand command) {
        this.usersRolesService.create(new UserSystemSetRolesDto(command.getId(), command.getRoles()));
//
//        this.systemService.updateRoles(UserRolesDto
//                .builder()
//                .roles(get(command.getRoles()))
//                .userId(command.getId())
//                .build());
    }

    private List<ManageRolDto> get(List<UUID> ids) {
        return ids.stream()
            .map(this.roleService::findById)
            .collect(Collectors.toList());
    }
}