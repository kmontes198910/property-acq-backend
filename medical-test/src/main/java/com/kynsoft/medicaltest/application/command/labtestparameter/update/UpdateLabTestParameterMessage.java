package com.kynsoft.medicaltest.application.command.labtestparameter.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Mensaje de respuesta para la actualización de un parámetro de examen de laboratorio
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLabTestParameterMessage implements ICommandMessage {

    private UUID id;
    private String message;

    /**
     * Constructor que crea un mensaje de confirmación con un ID
     *
     * @param id El ID del parámetro actualizado
     */
    public UpdateLabTestParameterMessage(UUID id) {
        this.id = id;
        this.message = "Parámetro de examen de laboratorio actualizado exitosamente";
    }
}
