package com.kynsoft.medicaltest.application.command.examination.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

/**
 * Mensaje de respuesta para el comando de creación de examen
 */
@Getter
public class CreateExaminationMessage implements ICommandMessage {
    private final UUID id;
    
    public CreateExaminationMessage(UUID id) {
        this.id = id;
    }
}
