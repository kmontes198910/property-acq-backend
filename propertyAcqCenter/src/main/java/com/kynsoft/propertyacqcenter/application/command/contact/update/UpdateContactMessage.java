package com.kynsoft.propertyacqcenter.application.command.contact.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UpdateContactMessage implements ICommandMessage {

    private final String command = "UPDATE_CONTACT";

    private final UUID id;
}
