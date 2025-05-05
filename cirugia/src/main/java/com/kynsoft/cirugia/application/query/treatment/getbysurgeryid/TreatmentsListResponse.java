package com.kynsoft.cirugia.application.query.treatment.getbysurgeryid;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.cirugia.domain.dto.Treatment;
import lombok.Getter;

import java.util.List;

@Getter
public class TreatmentsListResponse implements IResponse {
    private final List<Treatment> treatments;
    
    public TreatmentsListResponse(List<Treatment> treatments) {
        this.treatments = treatments;
    }
}