package com.kynsof.identity.domain.rules.manageRole;

import com.kynsof.identity.domain.interfaces.service.IManageRoleService;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.rules.BusinessRule;

import java.util.UUID;

public class ManageRoleCodeMustBeUniqueRule extends BusinessRule {

    private final IManageRoleService service;

    private final String code;

    private final UUID id;

    public ManageRoleCodeMustBeUniqueRule(IManageRoleService service, String code, UUID id) {
        super(
                DomainErrorMessage.PERMISSION_CODE_MUST_BY_UNIQUE,
                new ErrorField("code", "The role code must be unique.")
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
