package com.kynsoft.propertyacqcenter.application.command.AdquisitionPropertyTitleCompany.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateAdquisitionPropertyTitleCompanyMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_ADQUISITION";

    public CreateAdquisitionPropertyTitleCompanyMessage(UUID id) {
        this.id = id;
    }

}
