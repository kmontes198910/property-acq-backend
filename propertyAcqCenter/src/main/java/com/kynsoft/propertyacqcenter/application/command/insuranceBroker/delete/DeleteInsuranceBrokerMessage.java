package com.kynsoft.propertyacqcenter.application.command.insuranceBroker.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class DeleteInsuranceBrokerMessage implements ICommandMessage {
    private final String command = "DELETE_INSURANCE_BROKER";

    private final UUID id;

}
