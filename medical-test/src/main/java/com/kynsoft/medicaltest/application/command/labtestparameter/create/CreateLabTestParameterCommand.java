package com.kynsoft.medicaltest.application.command.labtestparameter.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Comando para crear un nuevo parámetro de examen de laboratorio
 */
@Getter
@AllArgsConstructor
@Setter
public class CreateLabTestParameterCommand implements ICommand {
    private  UUID id;
    private final UUID labTestId;
    private String code;
    private final String name;
    private final String unit;
    private final BigDecimal referenceRangeMin;
    private final BigDecimal referenceRangeMax;
    private final Boolean genderSpecific;
    private final UUID userId;

    /**
     * Crea un comando a partir de una solicitud y el ID del usuario
     *
     * @param request La solicitud con los datos del parámetro
     * @param userId ID del usuario que realiza la acción
     * @return El comando creado
     */
    public static CreateLabTestParameterCommand fromRequest(CreateLabTestParameterRequest request, UUID userId) {
        return new CreateLabTestParameterCommand(
                UUID.randomUUID(),
                request.getLabTestId(),
                request.getCode(),
                request.getName(),
                request.getUnit(),
                request.getReferenceRangeMin(),
                request.getReferenceRangeMax(),
                request.getGenderSpecific(),
                userId
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateLabTestParameterMessage(id);
    }
}
