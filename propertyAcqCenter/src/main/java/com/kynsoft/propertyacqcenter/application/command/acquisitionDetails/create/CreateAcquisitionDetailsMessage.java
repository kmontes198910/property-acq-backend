package com.kynsoft.propertyacqcenter.application.command.acquisitionDetails.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateAcquisitionDetailsMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_ACQUISITION_DETAILS";

    public CreateAcquisitionDetailsMessage(UUID id) {
        this.id = id;
    }

}
