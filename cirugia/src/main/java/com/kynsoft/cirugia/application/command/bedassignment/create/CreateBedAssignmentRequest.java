package com.kynsoft.cirugia.application.command.bedassignment.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String surgeryStage;
    private String observations;
    private UUID businessId;
    private UUID assignedBy;
}
