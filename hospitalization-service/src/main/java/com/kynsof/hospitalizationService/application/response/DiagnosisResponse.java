package com.kynsof.hospitalizationService.application.response;

import com.kynsof.hospitalizationService.domain.dto.*;
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
public class DiagnosisResponse implements IResponse {
    private UUID id;
    private EmergencyCaseDto emergencyCase;
    private String diagnosisType;
    private String diagnosisDescription;
    private String code;

    public DiagnosisResponse(DiagnosisDto dto) {
        this.id = dto.getId();
        this.emergencyCase = dto.getEmergencyCase();
        this.diagnosisType = dto.getDiagnosisType();
        this.diagnosisDescription = dto.getDiagnosisDescription();
        this.code = dto.getCode();
    }

}
