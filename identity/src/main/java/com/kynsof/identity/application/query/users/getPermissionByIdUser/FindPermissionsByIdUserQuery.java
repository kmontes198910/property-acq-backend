package com.kynsof.identity.application.query.users.getPermissionByIdUser;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;

import java.util.UUID;

@Getter
public class FindPermissionsByIdUserQuery implements IQuery {

    private final UUID id;

    public FindPermissionsByIdUserQuery(UUID id) {
        this.id = id;
    }

}
