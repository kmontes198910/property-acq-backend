package com.kynsof.identity.application.command.manageRole.create;


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
}
