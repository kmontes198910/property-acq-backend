package com.kynsoft.propertyacqcenter.application.command.insuranceBroker.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateInsuranceBrokerMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_INSURANCE_BROKER";

    public UpdateInsuranceBrokerMessage(UUID id) {
        this.id = id;
    }

}
