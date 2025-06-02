package com.kynsoft.propertyacqcenter.application.command.insurance.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateInsuranceMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_INSURANCE";

    public UpdateInsuranceMessage(UUID id) {
        this.id = id;
    }

}
