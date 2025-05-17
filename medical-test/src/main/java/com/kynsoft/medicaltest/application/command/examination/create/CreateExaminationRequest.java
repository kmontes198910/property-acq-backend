package com.kynsoft.medicaltest.application.command.examination.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Objeto de solicitud para la creación de un examen médico
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateExaminationRequest {
    private UUID orderId;
    private String code;
    private String examinationType;
    private String status;
    private String observations;
}
