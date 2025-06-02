package com.kynsoft.propertyacqcenter.application.command.property.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeletePropertyMessage implements ICommandMessage {
    private final String command = "DELETE_PROPERTY";

    private final String id;

}
