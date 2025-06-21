package com.kynsoft.medicaltest.application.command.labtestparameter.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Comando para actualizar un parámetro de examen de laboratorio existente
 */
@Getter
@AllArgsConstructor
public class UpdateLabTestParameterCommand implements ICommand {
    
    private final UUID id;
    private final String code;
    private final String name;
    private final UUID userId;
    private UUID labTestId;

    /**
     * Crea un comando a partir de una solicitud, ID del parámetro y ID del usuario
     *
     * @param request La solicitud con los datos actualizados
     * @param id ID del parámetro a actualizar
     * @param userId ID del usuario que realiza la acción
     * @return El comando creado
     */
    public static UpdateLabTestParameterCommand fromRequest(UpdateLabTestParameterRequest request, UUID id, UUID userId) {
        return new UpdateLabTestParameterCommand(
                id,
                request.getCode(),
                request.getName(),
                userId,
               request.getLabTestId()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateLabTestParameterMessage(id);
    }
}
