package com.kynsof.identity.application.query.users.getPermissionByIdUser;

import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;
import lombok.Builder;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class ModulePermissionResponse implements IResponse {
    private UUID id;
    private String name;
}