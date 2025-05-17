package com.kynsoft.medicaltest.application.command.examination.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Objeto de solicitud para la actualización de un examen médico
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateExaminationRequest {
    private String examinationType;
    private String status;
    private String results;
    private String observations;
}
