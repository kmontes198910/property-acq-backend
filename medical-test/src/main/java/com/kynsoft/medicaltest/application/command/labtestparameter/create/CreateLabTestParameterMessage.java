package com.kynsoft.medicaltest.application.command.labtestparameter.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Mensaje de respuesta para la creación de un parámetro de examen de laboratorio
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateLabTestParameterMessage implements ICommandMessage {

    private UUID id;
    private String message;

    /**
     * Constructor que crea un mensaje de confirmación con un ID
     *
     * @param id El ID del parámetro creado
     */
    public CreateLabTestParameterMessage(UUID id) {
        this.id = id;
        this.message = "Parámetro de examen de laboratorio creado exitosamente";
    }
}
