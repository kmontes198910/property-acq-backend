package com.kynsoft.medicaltest.application.command.examination.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Comando para crear un nuevo examen
 */
@Getter
@Setter
public class CreateExaminationCommand implements ICommand {
    
    private UUID id;
    
    @NotNull(message = "El ID de la orden no puede ser nulo")
    private UUID orderId;
    
    private String code;
    
    @NotEmpty(message = "El tipo de examen no puede estar vacío")
    private String examinationType;
    
    private String status;
    
    private String observations;
    
    private UUID createdBy;
    
    public CreateExaminationCommand() {
        this.id = UUID.randomUUID();
    }
    
    public static CreateExaminationCommand fromRequest(CreateExaminationRequest request, String userId) {
        CreateExaminationCommand command = new CreateExaminationCommand();
        command.setOrderId(request.getOrderId());
        command.setCode(request.getCode());
        command.setExaminationType(request.getExaminationType());
        command.setStatus(request.getStatus());
        command.setObservations(request.getObservations());
        command.setCreatedBy(userId != null ? UUID.fromString(userId) : null);
        return command;
    }
    
    @Override
    public ICommandMessage getMessage() {
        return new CreateExaminationMessage(id);
    }
}
