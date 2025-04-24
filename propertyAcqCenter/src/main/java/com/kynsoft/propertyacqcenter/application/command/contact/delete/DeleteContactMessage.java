package com.kynsoft.propertyacqcenter.application.command.contact.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class DeleteContactMessage implements ICommandMessage {

    private final String command = "DELETE_CONTACT";

    private final UUID id;
}
