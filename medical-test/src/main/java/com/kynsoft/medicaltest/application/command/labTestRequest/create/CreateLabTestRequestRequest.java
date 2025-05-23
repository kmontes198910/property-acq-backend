package com.kynsoft.medicaltest.application.command.labTestRequest.create;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateLabTestRequestRequest {
    private UUID patientId;
    private UUID doctorId;
    private LocalDate creationDate;
    private String status;
    private String observations;
    private UUID businessId;
    private List<LabTestItemRequestRequest> labTestItems;
    private boolean isActive;
}
