package com.kynsof.hospitalizationService.application.response;

import com.kynsof.hospitalizationService.domain.dto.EmergencyCaseDto;
import com.kynsof.hospitalizationService.domain.dto.PatientDto;
import com.kynsof.share.core.domain.bus.query.IResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmergencyCaseResponse implements IResponse {
    private UUID id;
    private PatientDto patient;
    private LocalDate admissionDate;
    private LocalTime admissionTime;
    private String admissionType;
    private String status;

    public EmergencyCaseResponse(EmergencyCaseDto dto) {
        this.id = dto.getId();
        this.patient = dto.getPatient();
        this.admissionDate = dto.getAdmissionDate();
        this.admissionTime = dto.getAdmissionTime();
        this.admissionType = dto.getAdmissionType();
        this.status = dto.getStatus();
    }
    
}
