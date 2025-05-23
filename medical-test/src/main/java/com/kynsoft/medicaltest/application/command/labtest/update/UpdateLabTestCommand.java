package com.kynsoft.medicaltest.application.command.labtest.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Comando para actualizar un examen de laboratorio existente
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLabTestCommand implements ICommand {
    private UUID id;
    private String code;
    private String name;
    private String category;
    private String description;
    private UUID updatedBy;
    
    /**
     * Convierte una solicitud de actualización en un comando
     * 
     * @param request La solicitud con los datos actualizados
     * @param id El ID del examen a actualizar
     * @param userId ID del usuario que realiza la acción
     * @return Un nuevo comando para actualizar un examen
     */
    public static UpdateLabTestCommand fromRequest(UpdateLabTestRequest request, UUID id, UUID userId) {
        return UpdateLabTestCommand.builder()
                .id(id)
                .code(request.getCode())
                .name(request.getName())
                .category(request.getCategory())
                .description(request.getDescription())
                .updatedBy(userId)
                .build();
    }


    @Override
    public ICommandMessage getMessage() {
        return new UpdateLabTestMessage(id);
    }
}
