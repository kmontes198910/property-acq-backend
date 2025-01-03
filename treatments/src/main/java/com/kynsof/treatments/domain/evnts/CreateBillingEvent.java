package com.kynsof.treatments.domain.evnts;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class CreateBillingEvent {
    private final   UUID patientId;
    private final   UUID businessId;
    private final List<String> examenCodeList;


    public CreateBillingEvent(UUID patientId, UUID businessId, List<String> examenIds) {

        this.patientId = patientId;
        this.businessId = businessId;
        this.examenCodeList = examenIds;
    }
}
