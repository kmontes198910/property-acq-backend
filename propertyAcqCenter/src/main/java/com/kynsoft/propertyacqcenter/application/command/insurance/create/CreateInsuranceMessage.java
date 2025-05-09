package com.kynsoft.propertyacqcenter.application.command.insurance.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateInsuranceMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_INSURANCE";

    public CreateInsuranceMessage(UUID id) {
        this.id = id;
    }

}
