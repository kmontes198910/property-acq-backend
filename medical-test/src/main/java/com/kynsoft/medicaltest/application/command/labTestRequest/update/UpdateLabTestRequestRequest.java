package com.kynsoft.medicaltest.application.command.labTestRequest.update;

import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLabTestRequestRequest {
    private String status;
    private String observations;
    private String origen;
}
