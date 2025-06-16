package com.kynsof.identity.application.command.manageRole.update;


import com.kynsof.identity.domain.dto.ManageRolDto;
import com.kynsof.identity.domain.interfaces.service.IManageRoleService;
import com.kynsof.identity.domain.rules.manageRole.ManageRoleCodeMustBeUniqueRule;
import com.kynsof.share.core.domain.RulesChecker;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.rules.ValidateObjectNotNullRule;
import com.kynsof.share.utils.UpdateIfNotNull;
import org.springframework.stereotype.Component;

@Component
public class UpdateManageRoleCommandHandler implements ICommandHandler<UpdateManageRoleCommand> {

    private final IManageRoleService service;


    public UpdateManageRoleCommandHandler(IManageRoleService service) {
        this.service = service;
    }

    @Override
    public void handle(UpdateManageRoleCommand command) {
        RulesChecker.checkRule(new ValidateObjectNotNullRule(command.getId(), "id", "Role ID cannot be null."));

        ManageRolDto update = this.service.findById(command.getId());

        if (command.getCode()!= null || !command.getCode().isEmpty()) {
            RulesChecker.checkRule(new ManageRoleCodeMustBeUniqueRule(this.service, command.getCode(), command.getId()));
            update.setCode(command.getCode());
        }

        UpdateIfNotNull.updateIfNotNull(update::setName, command.getName());

        UpdateIfNotNull.updateIfNotNull(update::setCode, command.getCode());

        service.update(update);
    }
}
