package com.kynsoft.propertyacqcenter.application.command.manageRole.create;


import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateManageRoleRequest {
    private String code;
    private String name;
    private List<UUID> documentTypes;
}
