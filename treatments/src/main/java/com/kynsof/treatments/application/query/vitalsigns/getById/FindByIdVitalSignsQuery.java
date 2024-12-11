package com.kynsof.treatments.application.query.vitalsigns.getById;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;

import java.util.UUID;

@Getter
public class FindByIdVitalSignsQuery implements IQuery {

    private final UUID id;

    public FindByIdVitalSignsQuery(UUID id) {
        this.id = id;
    }

}
