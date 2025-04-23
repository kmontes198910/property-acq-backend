package com.kynsoft.propertyacqcenter.domain.rules.employee;

import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.rules.BusinessRule;
import com.kynsoft.propertyacqcenter.domain.services.IEmployeeService;

import java.util.UUID;

public class EmployeeEmailMustBeUniqueRule extends BusinessRule {

    private final IEmployeeService service;

    private final String email;

    private final UUID id;

    public EmployeeEmailMustBeUniqueRule(IEmployeeService service, String email, UUID id) {
        super(
                DomainErrorMessage.MUST_BY_UNIQUE, 
                new ErrorField("email", "The email " + DomainErrorMessage.MUST_BY_UNIQUE.getReasonPhrase())
        );
        this.service = service;
        this.email = email;
        this.id = id;
    }

    @Override
    public boolean isBroken() {
        boolean result = this.service.countByEmailAndNotId(email, id) > 0;
        return result;
    }

}
