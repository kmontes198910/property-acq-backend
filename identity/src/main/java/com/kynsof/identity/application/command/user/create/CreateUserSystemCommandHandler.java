package com.kynsof.identity.application.command.user.create;

import com.kynsof.identity.application.command.auth.registrySystemUser.UserSystemKycloackRequest;
import com.kynsof.identity.domain.dto.BusinessDto;
import com.kynsof.identity.domain.dto.UserPermissionBusinessDto;
import com.kynsof.identity.domain.dto.UserStatus;
import com.kynsof.identity.domain.dto.UserSystemDto;
import com.kynsof.identity.domain.interfaces.service.*;
import com.kynsof.identity.domain.rules.usersystem.ModuleEmailMustBeUniqueRule;
import com.kynsof.identity.domain.rules.usersystem.ModuleUserNameMustBeUniqueRule;
import com.kynsof.identity.infrastructure.identity.Permission;
import com.kynsof.identity.infrastructure.identity.UserTypePermission;
import com.kynsof.share.core.domain.RulesChecker;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CreateUserSystemCommandHandler implements ICommandHandler<CreateUserSystemCommand> {

    private final IUserSystemService userSystemService;
    private final IAuthService authService;
    private final IUserPermissionBusinessService service;
    private final IPermissionService permissionService;
    private final IBusinessService businessService;
    private final IUserTypePermissionService userTypePermissionService;

    @Autowired
    public CreateUserSystemCommandHandler(IUserSystemService userSystemService, IAuthService authService,
                                          IUserPermissionBusinessService service, IPermissionService permissionService,
                                          IBusinessService businessService,
                                          IUserTypePermissionService userTypePermissionService) {
        this.userSystemService = userSystemService;
        this.authService = authService;
        this.service = service;
        this.permissionService = permissionService;
        this.businessService = businessService;
        this.userTypePermissionService = userTypePermissionService;
    }

    @Override
    public void handle(CreateUserSystemCommand command) {
        RulesChecker.checkRule(new ModuleEmailMustBeUniqueRule(this.userSystemService, command.getEmail(), UUID.randomUUID()));
        RulesChecker.checkRule(new ModuleUserNameMustBeUniqueRule(this.userSystemService, command.getUserName(), UUID.randomUUID()));

        UserSystemKycloackRequest userSystemRequest = new UserSystemKycloackRequest(
                command.getUserName(),
                command.getEmail(),
                command.getName(),
                command.getLastName(),
                command.getPassword(),
                command.getUserType()
        );
        String userId = authService.registerUserSystem(userSystemRequest, true);

        UserSystemDto userDto = new UserSystemDto(
                command.getId(),
                command.getUserName(),
                command.getEmail(),
                command.getName(),
                command.getLastName(),
                UserStatus.ACTIVE,
                command.getImage()
        );
        userDto.setKeyCloakId(UUID.fromString(userId));
        userDto.setUserName(command.getUserName());
        userDto.setUserType(command.getUserType());

        UUID id = userSystemService.create(userDto);
        command.setId(id);
        if (command.getBusinessId() != null) {
            addPermission(command,userDto);
        }

    }

    private void addPermission(CreateUserSystemCommand command,  UserSystemDto userSystemDto) {
        List<UserPermissionBusinessDto> userRoleBusinessDtos = new ArrayList<>();
        BusinessDto businessDto = this.businessService.findById(UUID.fromString(command.getBusinessId()));
        List< UserTypePermission> userTypePermissions = userTypePermissionService.getPermissionsByUserType(command.getUserType());
        List<Permission> permissions = userTypePermissions.stream().map(UserTypePermission::getPermission).toList();
        service.delete(UUID.fromString(command.getBusinessId()), userSystemDto.getId());
        for (Permission permission : permissions) {

            userRoleBusinessDtos.add(new UserPermissionBusinessDto(UUID.randomUUID(), userSystemDto, permission.toAggregate(), businessDto));
        }
        this.service.create(userRoleBusinessDtos);
    }
}
