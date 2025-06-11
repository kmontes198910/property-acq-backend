package com.kynsoft.settings.domain.rules.service;


import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.rules.BusinessRule;
import com.kynsoft.settings.domain.services.IServiceService;

import java.util.UUID;

public class ServiceCodeMustBeUniqueRule extends BusinessRule {

    private final IServiceService service;

    private final String code;

    private final UUID id;

    public ServiceCodeMustBeUniqueRule(IServiceService service, String code, UUID id) {
        super(
                DomainErrorMessage.SERVICE_CODE_MUST_BY_UNIQUE,
                new ErrorField("code", DomainErrorMessage.SERVICE_CODE_MUST_BY_UNIQUE.getReasonPhrase())
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
