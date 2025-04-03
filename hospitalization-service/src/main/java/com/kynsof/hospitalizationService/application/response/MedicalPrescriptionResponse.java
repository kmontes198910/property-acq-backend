package com.kynsof.hospitalizationService.application.response;

import com.kynsof.hospitalizationService.domain.dto.*;
import com.kynsof.share.core.domain.bus.query.IResponse;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MedicalPrescriptionResponse implements IResponse {
    private UUID id;
    private HospitalizationDto hospitalization;
    private LocalDate prescriptionDate;
    private String instructions;

    public MedicalPrescriptionResponse(MedicalPrescriptionDto dto) {
        this.id = dto.getId();
        this.hospitalization = dto.getHospitalization();
        this.prescriptionDate = dto.getPrescriptionDate();
        this.instructions = dto.getInstructions();
    }

}
