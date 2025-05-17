package com.kynsoft.medicaltest.application.command.examination.complete;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompleteExaminationRequest {
    @NotNull(message = "El ID del examen no puede ser nulo")
    private UUID examinationId;
    
    @NotBlank(message = "Los resultados no pueden estar vacíos")
    private String results;
}
