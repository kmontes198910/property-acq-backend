package com.kynsoft.propertyacqcenter.application.command.manageRole.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UpdateManageRoleMessage implements ICommandMessage {

    private final String command= "UPDATE_MANAGE_ROLE";

    private final UUID id;
}
