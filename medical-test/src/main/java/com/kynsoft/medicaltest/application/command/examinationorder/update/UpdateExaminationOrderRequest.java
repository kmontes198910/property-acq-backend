package com.kynsoft.medicaltest.application.command.examinationorder.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Objeto de solicitud para la actualización de una orden de exámenes
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateExaminationOrderRequest {
    private UUID doctorId;
    private LocalDateTime creationDate;
    private String status;
    private String observations;
    private UUID businessId;
}
