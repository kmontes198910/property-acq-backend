package com.kynsoft.medicaltest.application.command.examinationorder.create;

import com.kynsoft.medicaltest.application.command.examination.create.CreateExaminationRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Objeto de solicitud para la creación de una orden de exámenes
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateExaminationOrderRequest {
    private UUID patientId;
    private UUID doctorId;
    private LocalDateTime creationDate;
    private String status;
    private String observations;
    private UUID businessId;
    
    @Builder.Default
    private List<CreateExaminationRequest> examinations = new ArrayList<>();
}
