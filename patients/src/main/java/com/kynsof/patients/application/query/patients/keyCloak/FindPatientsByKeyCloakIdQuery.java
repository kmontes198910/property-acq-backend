package com.kynsof.patients.application.query.patients.keyCloak;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;

import java.util.UUID;

@Getter
public class FindPatientsByKeyCloakIdQuery implements IQuery {

    private final UUID id;

    public FindPatientsByKeyCloakIdQuery(UUID id) {
        this.id = id;
    }

}
