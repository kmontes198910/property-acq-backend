package com.kynsoft.propertyacqcenter.application.command.employee.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UpdateEmployeeMessage implements ICommandMessage {

    private final String command= "UPDATE_EMPLOYEE";

    private final UUID id;
}
