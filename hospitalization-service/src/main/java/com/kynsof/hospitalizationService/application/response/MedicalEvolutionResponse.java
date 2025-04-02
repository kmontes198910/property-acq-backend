package com.kynsof.hospitalizationService.application.response;

import com.kynsof.hospitalizationService.domain.dto.HospitalizationDto;
import com.kynsof.hospitalizationService.domain.dto.MedicalEvolutionDto;
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
public class MedicalEvolutionResponse implements IResponse {
    private UUID id;
    private HospitalizationDto hospitalization;
    private LocalDate recordDate;
    private String observations;
    private String diagnosis;

    public MedicalEvolutionResponse(MedicalEvolutionDto dto) {
        this.id = dto.getId();
        this.hospitalization = dto.getHospitalization();
        this.recordDate = dto.getRecordDate();
        this.observations = dto.getObservations();
        this.diagnosis = dto.getDiagnosis();
    }

}
