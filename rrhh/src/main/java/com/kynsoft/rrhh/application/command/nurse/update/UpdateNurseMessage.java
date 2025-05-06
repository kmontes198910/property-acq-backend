package com.kynsoft.rrhh.application.command.nurse.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsof.share.core.domain.response.ApiResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateNurseMessage implements ICommandMessage {
    private UUID id;

    public UpdateNurseMessage(UUID id) {
        this.id = id;
    }
}