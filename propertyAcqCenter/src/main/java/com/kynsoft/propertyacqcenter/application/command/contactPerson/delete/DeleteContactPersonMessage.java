package com.kynsoft.propertyacqcenter.application.command.contactPerson.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class DeleteContactPersonMessage implements ICommandMessage {

    private final String command = "DELETE_CONTACT_PERSON";

    private final UUID id;
}
