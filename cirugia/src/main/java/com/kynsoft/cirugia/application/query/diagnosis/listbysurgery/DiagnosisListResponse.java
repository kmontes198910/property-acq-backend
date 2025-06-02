package com.kynsoft.cirugia.application.query.diagnosis.listbysurgery;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.cirugia.domain.dto.Diagnosis;
import lombok.Getter;

import java.util.List;

@Getter
public class DiagnosisListResponse implements IResponse {
    private final List<Diagnosis> diagnoses;

    public DiagnosisListResponse(List<Diagnosis> diagnoses) {
        this.diagnoses = diagnoses;
    }
}