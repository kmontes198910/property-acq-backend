package com.kynsoft.settings.domain.rules.service;


import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.rules.BusinessRule;
import com.kynsoft.settings.domain.services.IServiceService;

import java.util.UUID;

public class SeviceNameMustBeUniqueRule extends BusinessRule {

    private final IServiceService service;

    private final String name;

    private final UUID id;

    public SeviceNameMustBeUniqueRule(IServiceService service, String name, UUID id) {
        super(
                DomainErrorMessage.SERVICE_NAME_MUST_BY_UNIQUE,
                new ErrorField("name", "The service name must be unique.")
        );
        this.service = service;
        this.name = name;
        this.id = id;
    }

    @Override
    public boolean isBroken() {
        return this.service.countByNameAndNotId(name, id) > 0;
    }

}
