package com.kynsoft.cirugia.application.query.doctor.getbyid;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;

import java.util.UUID;

@Getter
public class GetDoctorByIdQuery implements IQuery {
    private final UUID id;

    public GetDoctorByIdQuery(UUID id) {
        this.id = id;
    }
}