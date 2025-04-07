package com.kynsof.hospitalizationService.application.response;

import com.kynsof.hospitalizationService.domain.dto.EmergencyCaseDto;
import com.kynsof.hospitalizationService.domain.dto.PatientDto;
import com.kynsof.share.core.domain.bus.query.IResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmergencyCaseAndBedResponse implements IResponse {
    private UUID id;
    private PatientDto patient;
    private LocalDate admissionDate;
    private LocalTime admissionTime;
    private String admissionType;
    private String status;
    private List<BedResponse> beds;

    public EmergencyCaseAndBedResponse(EmergencyCaseDto dto, List<BedResponse> beds) {
        this.id = dto.getId();
        this.patient = dto.getPatient();
        this.admissionDate = dto.getAdmissionDate();
        this.admissionTime = dto.getAdmissionTime();
        this.admissionType = dto.getAdmissionType();
        this.status = dto.getStatus();
        this.beds = beds;
    }
    
}
