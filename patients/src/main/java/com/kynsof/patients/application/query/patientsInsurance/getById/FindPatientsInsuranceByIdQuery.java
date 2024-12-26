package com.kynsof.patients.application.query.patientsInsurance.getById;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;

import java.util.UUID;

@Getter
public class FindPatientsInsuranceByIdQuery implements IQuery {

    private final UUID id;

    public FindPatientsInsuranceByIdQuery(UUID id) {
        this.id = id;
    }

}
