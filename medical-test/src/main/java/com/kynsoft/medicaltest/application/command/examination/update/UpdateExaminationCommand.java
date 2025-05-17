package com.kynsoft.medicaltest.application.command.examination.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Comando para actualizar un examen existente
 */
@Getter
@Setter
public class UpdateExaminationCommand implements ICommand {
    
    @NotNull(message = "El ID del examen no puede ser nulo")
    private UUID id;
    
    @NotEmpty(message = "El tipo de examen no puede estar vacío")
    private String examinationType;
    
    private String status;
    
    private String results;
    
    private String observations;
    
    private UUID updatedBy;
    
    public static UpdateExaminationCommand fromRequest(UUID id, UpdateExaminationRequest request, String userId) {
        UpdateExaminationCommand command = new UpdateExaminationCommand();
        command.setId(id);
        command.setExaminationType(request.getExaminationType());
        command.setStatus(request.getStatus());
        command.setResults(request.getResults());
        command.setObservations(request.getObservations());
        command.setUpdatedBy(userId != null ? UUID.fromString(userId) : null);
        return command;
    }
    
    @Override
    public ICommandMessage getMessage() {
        return new UpdateExaminationMessage(id);
    }
}
