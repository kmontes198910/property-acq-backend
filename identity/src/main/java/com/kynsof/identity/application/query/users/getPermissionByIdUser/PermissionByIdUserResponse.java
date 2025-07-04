package com.kynsof.identity.application.query.users.getPermissionByIdUser;

import com.kynsof.identity.domain.dto.PermissionDto;
import com.kynsof.identity.domain.dto.enumType.PermissionStatusEnm;
import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
import lombok.Builder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PermissionByIdUserResponse implements IResponse {

    private UUID id;
    private String code;
    private ModulePermissionResponse module;
    private PermissionStatusEnm status;
    private String action;

    public PermissionByIdUserResponse(PermissionDto response) {
        this.id = response.getId();
        this.code = response.getCode();
        this.module = response.getModule() != null ? ModulePermissionResponse
                .builder()
                .id(response.getModule().getId())
                .name(response.getModule().getName())
                .build() : null;
        this.status = response.getStatus();
        this.action = response.getAction();
    }

}
