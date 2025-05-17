package com.kynsoft.medicaltest.application.command.examination.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

/**
 * Mensaje de respuesta para el comando de actualización de examen
 */
@Getter
public class UpdateExaminationMessage implements ICommandMessage {
    private final UUID id;
    
    public UpdateExaminationMessage(UUID id) {
        this.id = id;
    }
}
