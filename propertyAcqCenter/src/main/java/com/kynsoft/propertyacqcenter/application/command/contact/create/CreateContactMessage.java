package com.kynsoft.propertyacqcenter.application.command.contact.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CreateContactMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_CONTACT";
}
