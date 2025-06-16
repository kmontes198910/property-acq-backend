package com.kynsof.identity.application.command.manageRole.create;



import com.kynsof.identity.domain.dto.ManageRolDto;
import com.kynsof.identity.domain.interfaces.service.IManageRoleService;
import com.kynsof.identity.domain.rules.manageRole.ManageRoleCodeMustBeNullRule;
import com.kynsof.identity.domain.rules.manageRole.ManageRoleCodeMustBeUniqueRule;
import com.kynsof.share.core.domain.RulesChecker;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class CreateManageRoleCommandHandler implements ICommandHandler<CreateManageRoleCommand> {
    private final IManageRoleService service;

    public CreateManageRoleCommandHandler(IManageRoleService service) {
        this.service = service;
    }

    @Override
    public void handle(CreateManageRoleCommand command) {
        RulesChecker.checkRule(new ManageRoleCodeMustBeNullRule(command.getCode()));
        RulesChecker.checkRule(new ManageRoleCodeMustBeUniqueRule(this.service, command.getCode(), command.getId()));

        ManageRolDto dto = new ManageRolDto(command.getId(), command.getCode(), command.getName(),false);

        service.create(dto);
    }
}
