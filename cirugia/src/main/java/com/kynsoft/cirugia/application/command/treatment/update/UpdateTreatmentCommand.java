package com.kynsoft.cirugia.application.command.treatment.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateTreatmentCommand implements ICommand {
    private UUID id;
    private String name;
    private String description;
    private Integer quantity;
    private String medicineUnit;
    private String status;
    private String process;
    private UUID updatedBy;
    
    public static UpdateTreatmentCommand fromRequest(UpdateTreatmentRequest request, UUID id, String userId) {
        UpdateTreatmentCommand command = new UpdateTreatmentCommand();
        command.setId(id);
        command.setName(request.getName());
        command.setDescription(request.getDescription());
        command.setQuantity(request.getQuantity());
        command.setMedicineUnit(request.getMedicineUnit());
        command.setStatus(request.getStatus());
        command.setProcess(request.getProcess());
        command.setUpdatedBy(userId != null ? UUID.fromString(userId) : null);
        return command;
    }
    
    @Override
    public ICommandMessage getMessage() {
        return new UpdateTreatmentMessage(id);
    }
}