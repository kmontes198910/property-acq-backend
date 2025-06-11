package com.kynsoft.settings.domain.rules.serviceType;


import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.rules.BusinessRule;
import com.kynsoft.settings.domain.services.IServiceTypeService;

import java.util.UUID;

public class SeviceTypeNameMustBeUniqueRule extends BusinessRule {

    private final IServiceTypeService service;

    private final String name;

    private final UUID id;

    public SeviceTypeNameMustBeUniqueRule(IServiceTypeService service, String name, UUID id) {
        super(
                DomainErrorMessage.SERVICE_TYPE_NAME_MUST_BY_UNIQUE,
                new ErrorField("name", "The service type name must be unique.")
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
