package com.kynsof.identity.application.command.user.update.setRoles;

import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserSystemSetRolesRequest {
    private List<UUID> roles;
}
