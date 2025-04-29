package com.kynsoft.cirugia.application.query;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.cirugia.domain.dto.Surgery;
import lombok.Getter;

import java.util.List;

@Getter
public class SurgeryListResponse implements IResponse {
    private final List<Surgery> surgeries;

    public SurgeryListResponse(List<Surgery> surgeries) {
        this.surgeries = surgeries;
    }
}