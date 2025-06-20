package com.kynsoft.propertyacqcenter.application.command.manageRole.updateDocumentType;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class UpdateManageRoleDocumentTypeRequest {

    private List<UUID> documentTypes;
}
