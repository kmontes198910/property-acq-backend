package com.kynsof.treatments.application.command.externalConsultation.update;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateTreatmentAllRequest {
    private String description;
    private UUID medication;
    private int quantity;
    private String medicineUnit;
}
