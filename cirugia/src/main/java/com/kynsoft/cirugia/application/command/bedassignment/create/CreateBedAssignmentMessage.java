package com.kynsoft.cirugia.application.command.bedassignment.create;

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
public class CreateBedAssignmentMessage implements ICommandMessage {
    private UUID id;
    private UUID patientId;
    private UUID surgeryId;
    private UUID bedId;
    private UUID roomId;
    private LocalDateTime assignmentDate;
    private String status;
    private String surgeryStage;
    private String observations;
    private UUID assignedBy;
    private UUID businessId;
}
