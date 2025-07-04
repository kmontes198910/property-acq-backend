package com.kynsof.identity.application.command.manageRole.update;


import com.kynsof.identity.domain.dto.ManageRolDto;
import com.kynsof.identity.domain.dto.PermissionDto;
import com.kynsof.identity.domain.interfaces.service.IManageRoleService;
import com.kynsof.identity.domain.interfaces.service.IPermissionService;
import com.kynsof.identity.domain.rules.manageRole.ManageRoleCodeMustBeUniqueRule;
import com.kynsof.identity.infrastructure.services.rabbitMq.eventPublisher.EventManageRolePublisherService;
import com.kynsof.share.core.domain.RulesChecker;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.rules.ValidateObjectNotNullRule;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class UpdateManageRoleCommandHandler implements ICommandHandler<UpdateManageRoleCommand> {

    private final IManageRoleService service;
    private final EventManageRolePublisherService manageRolePublisherService;
    private final IPermissionService permissionService;

    public UpdateManageRoleCommandHandler(IManageRoleService service, 
                                          EventManageRolePublisherService manageRolePublisherService,
                                          IPermissionService permissionService) {
        this.service = service;
        this.manageRolePublisherService = manageRolePublisherService;
        this.permissionService = permissionService;
    }

    @Override
    public void handle(UpdateManageRoleCommand command) {
        RulesChecker.checkRule(new ValidateObjectNotNullRule(command.getId(), "id", "Role ID cannot be null."));

        if (command.getCode()!= null || !command.getCode().isEmpty()) {
            RulesChecker.checkRule(new ManageRoleCodeMustBeUniqueRule(this.service, command.getCode(), command.getId()));
        }

        service.update(ManageRolDto
                .builder()
                .code(command.getCode())
                .name(command.getName())
                .id(command.getId())
                .permissions(command.getPermissions() != null ? get(command.getPermissions()) : null)
                .build());

        //this.manageRolePublisherService.publishManageRoleEvent(updatedManageRole);
    }

    
    private List<PermissionDto> get(List<UUID> ids) {
        return ids.stream()
            .map(this.permissionService::findById)
            .collect(Collectors.toList());
    }

}
