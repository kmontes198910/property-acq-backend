package com.kynsoft.propertyacqcenter.application.command.analysis.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateAnalysisMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_ANALYSIS";

    public CreateAnalysisMessage(UUID id) {
        this.id = id;
    }

}
