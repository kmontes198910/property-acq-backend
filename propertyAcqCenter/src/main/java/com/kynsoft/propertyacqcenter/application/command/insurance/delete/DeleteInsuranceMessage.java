package com.kynsoft.propertyacqcenter.application.command.insurance.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class DeleteInsuranceMessage implements ICommandMessage {
    private final String command = "DELETE_INSURANCE";

    private final UUID id;

}
