package com.kynsof.identity.domain.rules.manageRole;

import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.rules.BusinessRule;

public class ManageRoleCodeMustBeNullRule extends BusinessRule {

    private final String code;

    public ManageRoleCodeMustBeNullRule(String code) {
        super(
                DomainErrorMessage.PERMISSION_CODE_CANNOT_BE_EMPTY,
                new ErrorField("code", "The code of the role cannot be empty.")
        );
        this.code = code;
    }

    @Override
    public boolean isBroken() {
        return this.code == null || this.code.isEmpty();
    }

}