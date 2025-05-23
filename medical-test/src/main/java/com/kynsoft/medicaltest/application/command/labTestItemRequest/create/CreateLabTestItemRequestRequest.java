package com.kynsoft.medicaltest.application.command.labTestItemRequest.create;

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
public class CreateLabTestItemRequestRequest {
    private UUID order;
    private String code;
    private String examinationType;
    private String status;
    private LocalDate completionDate;
    private String observations;
}
