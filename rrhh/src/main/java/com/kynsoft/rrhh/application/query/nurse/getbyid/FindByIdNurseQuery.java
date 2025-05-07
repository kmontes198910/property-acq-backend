package com.kynsoft.rrhh.application.query.nurse.getbyid;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FindByIdNurseQuery implements IQuery {
    private UUID id;

    public FindByIdNurseQuery(UUID id) {
        this.id = id;
    }
}