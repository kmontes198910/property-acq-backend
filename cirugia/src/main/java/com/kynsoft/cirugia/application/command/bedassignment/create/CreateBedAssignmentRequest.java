package com.kynsoft.cirugia.application.command.bedassignment.create;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBedAssignmentRequest {
    private UUID patientId;
    private UUID surgeryId;
    private UUID bedId;
    private UUID roomId;
    private SurgeryStage surgeryStage;
    private String observations;
    private UUID businessId;
    private UUID assignedBy;
}
