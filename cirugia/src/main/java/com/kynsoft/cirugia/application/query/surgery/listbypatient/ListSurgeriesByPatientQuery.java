package com.kynsoft.cirugia.application.query.surgery.listbypatient;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ListSurgeriesByPatientQuery implements IQuery {
    private final UUID patientId;

    public ListSurgeriesByPatientQuery(UUID patientId) {
        this.patientId = patientId;
    }
}