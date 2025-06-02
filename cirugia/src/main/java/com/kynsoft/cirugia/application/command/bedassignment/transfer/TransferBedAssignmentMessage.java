package com.kynsoft.cirugia.application.command.bedassignment.transfer;

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
public class TransferBedAssignmentMessage implements ICommandMessage {
    private UUID id;
    private UUID patientId;
    private UUID surgeryId;
    private UUID bedId;
    private UUID roomId;
    private LocalDateTime assignmentDate;
    private String status;
    private String observations;
    private UUID assignedBy;
    private UUID businessId;
}
