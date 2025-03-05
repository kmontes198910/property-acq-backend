package com.kynsof.patients.application.query.patients.getByIdHttpReplicate;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;

import java.util.UUID;

@Getter
public class FindHttpPatientsByIdQuery  implements IQuery {

    private final UUID id;

    public FindHttpPatientsByIdQuery(UUID id) {
        this.id = id;
    }

}
