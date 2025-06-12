package com.kynsoft.medicaltest.application.command.labtest.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Comando para crear un nuevo examen de laboratorio
 */
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateLabTestCommand implements ICommand {
    private UUID id;
    private String code;
    private String name;
    private String description;
    private UUID createdBy;
    /**
     * Convierte una solicitud de creación en un comando
     * 
     * @param request La solicitud con los datos
     * @param userId ID del usuario que realiza la acción
     * @return Un nuevo comando para crear un examen
     */
    public static CreateLabTestCommand fromRequest(CreateLabTestRequest request, UUID userId) {
        return CreateLabTestCommand.builder()
                .id(UUID.randomUUID())
                .code(request.getCode())
                .name(request.getName())
                .description(request.getDescription())
                .createdBy(userId)
                .build();
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateLabTestMessage(id);
    }
}
