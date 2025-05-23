package com.kynsoft.medicaltest.application.command.labTestRequest.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteLabTestRequestMessage implements ICommandMessage {

    private String message;

    public DeleteLabTestRequestMessage() {
        this.message = "Examen de laboratorio eliminado exitosamente";
    }
}
