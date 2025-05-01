package com.kynsoft.propertyacqcenter.application.command.address.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateAddressMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_ADDRESS";

    public UpdateAddressMessage(UUID id) {
        this.id = id;
    }

}
