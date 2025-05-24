package com.kynsoft.medicaltest.application.command.labTestRequest.create;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateLabTestRequestRequest {
    @NotNull(message = "El id es obligatorio")
    private UUID id;

    @NotNull(message = "El paciente es obligatorio")
    private UUID patientId;

    @NotNull(message = "El doctor es obligatorio")
    private UUID doctorId;

    private String status;
    private String observations;

    @NotNull(message = "El businessId es obligatorio")
    private UUID businessId;

    private List<LabTestItemRequestRequest> labTestItems;
    private boolean isActive;
}