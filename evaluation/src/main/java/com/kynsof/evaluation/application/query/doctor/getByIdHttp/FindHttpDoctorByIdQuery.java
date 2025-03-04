package com.kynsof.evaluation.application.query.doctor.getByIdHttp;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;

import java.util.UUID;

@Getter
public class FindHttpDoctorByIdQuery  implements IQuery {

    private final UUID id;

    public FindHttpDoctorByIdQuery(UUID id) {
        this.id = id;
    }

}
