package com.kynsof.identity.application.query.users.getPermissionByIdUser;

import com.kynsof.identity.domain.dto.PermissionDto;
import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class RolesPermissionsUserResponse implements IResponse {
    private List<PermissionDto> permissions;
}