package com.kynsof.identity.domain.dto.exception.manageRole;

public class ManageRoleCodeIsNullException extends RuntimeException {
    public ManageRoleCodeIsNullException() {
        super("Code is empty.");
    }
}
