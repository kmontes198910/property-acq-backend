package com.kynsoft.medicaltest.application.command.examinationorder.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

/**
 * Mensaje de respuesta para el comando de creación de orden de exámenes
 */
@Getter
public class CreateExaminationOrderMessage implements ICommandMessage {
    private final UUID id;
    
    public CreateExaminationOrderMessage(UUID id) {
        this.id = id;
    }
}
