package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.titleCompany;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateAdquisitionPropertyTitleCompanyMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_ADQUISITION";

    public UpdateAdquisitionPropertyTitleCompanyMessage(UUID id) {
        this.id = id;
    }

}
