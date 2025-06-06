package com.kynsoft.cirugia.application.command.treatment.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateTreatmentCommand implements ICommand {
    private UUID id;
    private UUID surgeryId;
    private UUID patientId;
    private String name;
    private String description;
    private int quantity;
    private String medicineUnit;
    private String presentacion;
    private String status;
    private String process;
    private UUID createdBy;
    
    public CreateTreatmentCommand() {
        this.id = UUID.randomUUID();
    }
    
    public static CreateTreatmentCommand fromRequest(CreateTreatmentRequest request, String userId) {
        CreateTreatmentCommand command = new CreateTreatmentCommand();
        command.setSurgeryId(request.getSurgeryId());
        command.setPatientId(request.getPatientId());
        command.setName(request.getName());
        command.setDescription(request.getDescription());
        command.setQuantity(request.getQuantity());
        command.setMedicineUnit(request.getMedicineUnit());
        command.setPresentacion(request.getPresentacion());
        command.setStatus(request.getStatus());
        command.setProcess(request.getProcess());
        command.setCreatedBy(userId != null ? UUID.fromString(userId) : null);
        return command;
    }
    
    @Override
    public ICommandMessage getMessage() {
        return new CreateTreatmentMessage(id);
    }
}