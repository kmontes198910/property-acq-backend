package com.kynsoft.propertyacqcenter.application.command.allAnalysis.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateAllAnalysisMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_ANALYSIS";

    public CreateAllAnalysisMessage(UUID id) {
        this.id = id;
    }

}
