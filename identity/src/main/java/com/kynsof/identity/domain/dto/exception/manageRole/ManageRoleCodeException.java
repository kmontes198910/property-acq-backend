package com.kynsof.identity.domain.dto.exception.manageRole;

public class ManageRoleCodeException extends RuntimeException {
    public ManageRoleCodeException(String code) {
        super("Code: " + code +", already exists.");
    }
}
