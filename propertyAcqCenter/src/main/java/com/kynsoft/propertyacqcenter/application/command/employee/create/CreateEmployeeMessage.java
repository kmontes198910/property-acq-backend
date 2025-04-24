package com.kynsoft.propertyacqcenter.application.command.employee.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CreateEmployeeMessage implements ICommandMessage {
    private final UUID id;

    private final String command = "CREATE_EMPLOYEE";
}
