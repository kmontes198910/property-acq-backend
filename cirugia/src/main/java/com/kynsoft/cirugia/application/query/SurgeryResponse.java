package com.kynsoft.cirugia.application.query;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.cirugia.domain.model.Surgery;
import lombok.Getter;

@Getter
public class SurgeryResponse implements IResponse {
    private final Surgery surgery;

    public SurgeryResponse(Surgery surgery) {
        this.surgery = surgery;
    }
}