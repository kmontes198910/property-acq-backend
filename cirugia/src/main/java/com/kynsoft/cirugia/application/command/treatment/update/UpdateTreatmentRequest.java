package com.kynsoft.cirugia.application.command.treatment.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTreatmentRequest {
    private String name;
    private String description;
    private Integer quantity;
    private String medicineUnit;
    private String presentacion;
    private String status;
    private String process;
}