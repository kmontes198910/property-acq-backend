package com.kynsof.identity.application.query.manageRole.getByid;

import com.kynsof.identity.domain.dto.ManageRolDto;
import com.kynsof.identity.domain.dto.PermissionDto;
import com.kynsof.share.core.domain.bus.query.IResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ManageRoleResponse implements IResponse {
    private UUID id;
    private String code;
    private String name;
    private List<PermissionDto> permissions;

    public ManageRoleResponse(ManageRolDto dto) {
        this.id = dto.getId();
        this.code = dto.getCode();
        this.name = dto.getName();
        this.permissions = dto.getPermissions();
    }
}
