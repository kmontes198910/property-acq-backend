package com.kynsof.identity.application.command.manageRole.update;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateManageRoleRequest {
    private String code;
    private String name;
}
