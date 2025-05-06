package com.kynsoft.rrhh.application.command.nurse.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsof.share.core.domain.response.ApiResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateNurseMessage implements ICommandMessage {
    private UUID id;

    public CreateNurseMessage(UUID id) {
        this.id = id;
    }
}