package com.kynsof.treatments.application.query.dashboard.contConsultByYears;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ContConsultByYearsQuery implements IQuery {

    private final UUID businessId;
    private final int year;

    public ContConsultByYearsQuery(UUID id, int year) {
        this.businessId = id;
        this.year = year;
    }

}
