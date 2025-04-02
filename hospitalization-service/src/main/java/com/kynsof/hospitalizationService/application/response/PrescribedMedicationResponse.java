package com.kynsof.hospitalizationService.application.response;

import com.kynsof.hospitalizationService.domain.dto.MedicalPrescriptionDto;
import com.kynsof.hospitalizationService.domain.dto.PrescribedMedicationDto;
import com.kynsof.share.core.domain.bus.query.IResponse;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PrescribedMedicationResponse implements IResponse {
    private UUID id;
    private MedicalPrescriptionDto medicalPrescription;
    private String medicationName;
    private String dosage;
    private String frequency;
    private String administrationRoute;
    private Integer duration;

    public PrescribedMedicationResponse(PrescribedMedicationDto dto) {
        this.id = dto.getId();
        this.medicalPrescription = dto.getMedicalPrescription();
        this.medicationName = dto.getMedicationName();
        this.dosage = dto.getDosage();
        this.frequency = dto.getFrequency();
        this.administrationRoute = dto.getAdministrationRoute();
        this.duration = dto.getDuration();
    }

}
