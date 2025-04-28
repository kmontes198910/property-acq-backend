package com.kynsoft.cirugia.application.query.surgery.listbydaterange;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class ListSurgeriesByDateRangeQuery implements IQuery {
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final UUID businessId;

    public ListSurgeriesByDateRangeQuery(LocalDateTime startDate, LocalDateTime endDate, UUID businessId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.businessId = businessId;
    }
}