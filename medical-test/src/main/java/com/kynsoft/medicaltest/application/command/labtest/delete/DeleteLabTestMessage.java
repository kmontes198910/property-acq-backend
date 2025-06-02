package com.kynsoft.medicaltest.application.command.labtest.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Mensaje de respuesta para la eliminación exitosa de un examen de laboratorio
 */
@Getter

@AllArgsConstructor
public class DeleteLabTestMessage implements ICommandMessage {
    private String message;
    
    /**
     * Constructor que crea un mensaje de confirmación
     */
    public DeleteLabTestMessage() {
        this.message = "Examen de laboratorio eliminado exitosamente";
    }
}
