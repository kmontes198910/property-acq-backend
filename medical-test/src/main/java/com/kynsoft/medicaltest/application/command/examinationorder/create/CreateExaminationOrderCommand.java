package com.kynsoft.medicaltest.application.command.examinationorder.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.medicaltest.application.command.examination.create.CreateExaminationRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Comando para crear una nueva orden de exámenes
 */
@Getter
@Setter
public class CreateExaminationOrderCommand implements ICommand {
    
    private UUID id;
    
    @NotNull(message = "El ID del paciente no puede ser nulo")
    private UUID patientId;
    
    @NotNull(message = "El ID del doctor no puede ser nulo")
    private UUID doctorId;
    
    private LocalDateTime creationDate;
    
    private String status;
    
    private String observations;
    
    @NotNull(message = "El ID del negocio no puede ser nulo")
    private UUID businessId;
    
    private UUID createdBy;
    
    private List<CreateExaminationRequest> examinations = new ArrayList<>();
    
    public CreateExaminationOrderCommand() {
        this.id = UUID.randomUUID();
    }
    
    public static CreateExaminationOrderCommand fromRequest(CreateExaminationOrderRequest request, String userId) {
        CreateExaminationOrderCommand command = new CreateExaminationOrderCommand();
        command.setPatientId(request.getPatientId());
        command.setDoctorId(request.getDoctorId());
        command.setCreationDate(request.getCreationDate());
        command.setStatus(request.getStatus());
        command.setObservations(request.getObservations());
        command.setBusinessId(request.getBusinessId());
        command.setExaminations(request.getExaminations());
        command.setCreatedBy(userId != null ? UUID.fromString(userId) : null);
        return command;
    }
    
    @Override
    public ICommandMessage getMessage() {
        return new CreateExaminationOrderMessage(id);
    }
}
