package com.kynsoft.propertyacqcenter.application.command.employee.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class DeleteEmployeeMessage implements ICommandMessage {
    private final String command = "DELETE_EMPLOYEE";

    private final UUID id;

}
