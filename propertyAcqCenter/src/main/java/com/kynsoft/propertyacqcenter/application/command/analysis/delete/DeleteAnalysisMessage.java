package com.kynsoft.propertyacqcenter.application.command.analysis.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteAnalysisMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "DELETE_ANALYSIS";

    public DeleteAnalysisMessage(UUID id) {
        this.id = id;
    }

}
