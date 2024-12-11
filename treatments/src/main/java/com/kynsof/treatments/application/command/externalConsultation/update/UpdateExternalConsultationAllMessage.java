package com.kynsof.treatments.application.command.externalConsultation.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateExternalConsultationAllMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_EXTERNAL_CONSULTATION";

    public UpdateExternalConsultationAllMessage(UUID id) {
        this.id = id;
    }

}
