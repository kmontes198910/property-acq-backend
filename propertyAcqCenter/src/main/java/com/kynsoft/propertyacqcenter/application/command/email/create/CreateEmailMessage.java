package com.kynsoft.propertyacqcenter.application.command.email.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

@Getter
public class CreateEmailMessage implements ICommandMessage {

    private final String command = "SEND_EMAIL";

    public CreateEmailMessage() {}

}
