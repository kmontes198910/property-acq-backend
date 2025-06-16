package com.kynsof.identity.application.query.manageRole.getByid;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;

import java.util.UUID;


@Getter
public class FindManageRoleByIdQuery implements IQuery {

    private final UUID id;

    public FindManageRoleByIdQuery(UUID id) {
        this.id = id;
    }

}
