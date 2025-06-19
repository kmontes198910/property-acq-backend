package com.kynsoft.propertyacqcenter.application.command.manageRole.update;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class UpdateManageRoleRequest {

    private List<UUID> documentTypes;
}
