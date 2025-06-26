package com.kynsof.identity.application.command.manageRole.create;

import com.kynsof.identity.domain.dto.ManageRolDto;
import com.kynsof.identity.domain.dto.PermissionDto;
import com.kynsof.identity.domain.interfaces.service.IManageRoleService;
import com.kynsof.identity.domain.interfaces.service.IPermissionService;
import com.kynsof.identity.infrastructure.services.rabbitMq.eventPublisher.EventManageRolePublisherService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class CreateManageRoleCommandHandler implements ICommandHandler<CreateManageRoleCommand> {

    private final IManageRoleService service;
    private final EventManageRolePublisherService manageRolePublisherService;
    private final IPermissionService permissionService;

    public CreateManageRoleCommandHandler(IManageRoleService service, 
                                          EventManageRolePublisherService manageRolePublisherService,
                                          IPermissionService permissionService) {
        this.service = service;
        this.manageRolePublisherService = manageRolePublisherService;
        this.permissionService = permissionService;
    }

    @Override
    public void handle(CreateManageRoleCommand command) {
        //RulesChecker.checkRule(new ManageRoleCodeMustBeNullRule(command.getCode()));
        //RulesChecker.checkRule(new ManageRoleCodeMustBeUniqueRule(this.service, command.getCode(), command.getId()));

        this.service.validateNull(command.getCode());
        this.service.validateCode(command.getCode(), command.getId());
        ManageRolDto dto = new ManageRolDto(
                command.getId(), 
                command.getCode(), 
                command.getName(), 
                false,
                command.getPermissions() != null ? get(command.getPermissions()) : null
        );

        ManageRolDto createdManageRole = service.create(dto);
        manageRolePublisherService.publishManageRoleEvent(createdManageRole);
    }

    private List<PermissionDto> get(List<UUID> ids) {
        return ids.stream()
            .map(this.permissionService::findById)
            .collect(Collectors.toList());
    }
}
