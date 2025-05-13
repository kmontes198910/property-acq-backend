package com.kynsoft.cirugia.application.command.bedassignment.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBedAssignmentCommand implements ICommand {
    private UUID id;
    private UUID patientId;
    private UUID surgeryId;
    private UUID bedId;
    private UUID roomId;
    private String surgeryStage;
    private String observations;
    private UUID businessId;
    private UUID assignedBy;
    private UUID createdBy;
    
    private CreateBedAssignmentMessage message;
    
    public static CreateBedAssignmentCommand fromRequest(CreateBedAssignmentRequest request, String userId) {
        return CreateBedAssignmentCommand.builder()
                .patientId(request.getPatientId())
                .surgeryId(request.getSurgeryId())
                .bedId(request.getBedId())
                .roomId(request.getRoomId())
                .surgeryStage(request.getSurgeryStage() != null ? request.getSurgeryStage().getValue() : null)
                .observations(request.getObservations())
                .businessId(request.getBusinessId())
                .assignedBy(request.getAssignedBy())
                .createdBy(UUID.fromString(userId))
                .build();
    }
    
    @Override
    public ICommandMessage getMessage() {
        return message != null ? message : new CreateBedAssignmentMessage();
    }
}
