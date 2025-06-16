package com.kynsof.identity.application.command.manageRole.deleteAll;


import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class DeleteAllManageRoleRequest {
    private List<UUID> roles;
}
