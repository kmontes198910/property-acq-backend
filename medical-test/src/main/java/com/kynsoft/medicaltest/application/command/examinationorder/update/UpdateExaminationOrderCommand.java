package com.kynsoft.medicaltest.application.command.examinationorder.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Comando para actualizar una orden de exámenes existente
 */
@Getter
@Setter
public class UpdateExaminationOrderCommand implements ICommand {
    
    @NotNull(message = "El ID de la orden no puede ser nulo")
    private UUID id;
    
    private UUID doctorId;
    
    private LocalDateTime creationDate;
    
    private String status;
    
    private String observations;
    
    private UUID businessId;
    
    private UUID updatedBy;
    
    public static UpdateExaminationOrderCommand fromRequest(UUID id, UpdateExaminationOrderRequest request, String userId) {
        UpdateExaminationOrderCommand command = new UpdateExaminationOrderCommand();
        command.setId(id);
        command.setDoctorId(request.getDoctorId());
        command.setCreationDate(request.getCreationDate());
        command.setStatus(request.getStatus());
        command.setObservations(request.getObservations());
        command.setBusinessId(request.getBusinessId());
        command.setUpdatedBy(userId != null ? UUID.fromString(userId) : null);
        return command;
    }
    
    @Override
    public ICommandMessage getMessage() {
        return new UpdateExaminationOrderMessage(id);
    }
}
