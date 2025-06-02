package com.kynsoft.propertyacqcenter.application.command.insuranceBroker.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateInsuranceBrokerMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_INSURANCE_BROKER";

    public CreateInsuranceBrokerMessage(UUID id) {
        this.id = id;
    }

}
