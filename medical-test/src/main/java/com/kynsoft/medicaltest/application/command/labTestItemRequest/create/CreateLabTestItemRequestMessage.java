package com.kynsoft.medicaltest.application.command.labTestItemRequest.create;

import com.kynsoft.medicaltest.application.command.labTestRequest.create.*;
import com.kynsoft.medicaltest.application.command.labtest.create.*;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Mensaje de respuesta para la creación exitosa de un examen de laboratorio
 */
@Getter
@AllArgsConstructor
public class CreateLabTestItemRequestMessage implements ICommandMessage {
    private UUID id;
    private String message;
    
    /**
     * Constructor que crea un mensaje de confirmación con un ID
     * 
     * @param id El ID del examen creado
     */
    public CreateLabTestItemRequestMessage(UUID id) {
        this.id = id;
        this.message = "Examen de laboratorio creado exitosamente";
    }
}
