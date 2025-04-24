package com.kynsoft.propertyacqcenter.application.command.contactPerson.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UpdateContactPersonMessage implements ICommandMessage {

    private final String command = "UPDATE_CONTACT_PERSON";

    private final UUID id;
}
