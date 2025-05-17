package com.kynsoft.medicaltest.application.command.examinationorder.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

/**
 * Mensaje de respuesta para el comando de actualización de orden de exámenes
 */
@Getter
public class UpdateExaminationOrderMessage implements ICommandMessage {
    private final UUID id;
    
    public UpdateExaminationOrderMessage(UUID id) {
        this.id = id;
    }
}
