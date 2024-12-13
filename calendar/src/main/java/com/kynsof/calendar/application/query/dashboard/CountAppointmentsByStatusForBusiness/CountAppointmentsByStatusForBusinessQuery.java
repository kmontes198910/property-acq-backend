package com.kynsof.calendar.application.query.dashboard.CountAppointmentsByStatusForBusiness;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CountAppointmentsByStatusForBusinessQuery implements IQuery {

    private final UUID businessId;

    public CountAppointmentsByStatusForBusinessQuery(UUID businessId) {
        this.businessId = businessId;
    }

}
