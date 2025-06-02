package com.kynsoft.cirugia.application.query.surgery.listbybusiness;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ListSurgeriesByBusinessQuery implements IQuery {
    private final UUID businessId;

    public ListSurgeriesByBusinessQuery(UUID businessId) {
        this.businessId = businessId;
    }
}