package com.kynsof.identity.application.query.users.getPermissionByIdUser;

import com.kynsof.identity.domain.dto.ManageRolDto;
import com.kynsof.identity.domain.dto.PermissionDto;
import com.kynsof.identity.domain.dto.UserSystemDto;
import com.kynsof.share.core.domain.bus.query.IResponse;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class PermissionsByIdUserResponse implements IResponse {
    private List<PermissionByIdUserResponse> permissions = new ArrayList<>();

    public PermissionsByIdUserResponse(UserSystemDto userSystemDto) {
        if(userSystemDto.getRoles() != null) {
            for (ManageRolDto role : userSystemDto.getRoles()) {
                if (role.getPermissions() != null) {
                    for (PermissionDto permission : role.getPermissions()) {
                        this.permissions.add(new PermissionByIdUserResponse(permission));
                    }
                }
            }
        }
    }

}