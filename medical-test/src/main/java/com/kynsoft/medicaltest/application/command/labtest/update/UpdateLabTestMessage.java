package com.kynsoft.medicaltest.application.command.labtest.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Mensaje de respuesta para la actualización exitosa de un examen de laboratorio
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLabTestMessage implements ICommandMessage {
    private UUID id;
    private String message;

    /**
     * Constructor que crea un mensaje de confirmación con un ID
     *
     * @param id El ID del examen actualizado
     */
    public UpdateLabTestMessage(UUID id) {
        this.id = id;
        this.message = "Examen de laboratorio actualizado exitosamente";
    }
}
