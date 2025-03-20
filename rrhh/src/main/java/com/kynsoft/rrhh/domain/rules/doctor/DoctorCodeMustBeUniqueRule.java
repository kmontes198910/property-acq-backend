package com.kynsoft.rrhh.domain.rules.doctor;

import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.rules.BusinessRule;
import com.kynsoft.rrhh.domain.interfaces.services.IDoctorService;

import java.util.UUID;

public class DoctorCodeMustBeUniqueRule extends BusinessRule {

    private final IDoctorService service;

    private final String code;

    private final UUID id;

    public DoctorCodeMustBeUniqueRule(IDoctorService service, String code, UUID id) {
        super(
                DomainErrorMessage.MUST_BY_UNIQUE, 
                new ErrorField("code", "The code " + DomainErrorMessage.MUST_BY_UNIQUE.getReasonPhrase())
        );
        this.service = service;
        this.code = code;
        this.id = id;
    }

    @Override
    public boolean isBroken() {
        return this.service.countByCodeAndNotId(code, id) > 0;
    }

}
