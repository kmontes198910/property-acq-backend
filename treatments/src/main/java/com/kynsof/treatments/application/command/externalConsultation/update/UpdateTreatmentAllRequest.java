package com.kynsof.treatments.application.command.externalConsultation.update;

import com.kynsof.treatments.application.command.externalConsultation.create.OptometryExamRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UpdateTreatmentAllRequest {
    private UUID id; // Nuevo campo
    private String description;
    private UUID medication;
    private int quantity;
    private String medicineUnit;
}