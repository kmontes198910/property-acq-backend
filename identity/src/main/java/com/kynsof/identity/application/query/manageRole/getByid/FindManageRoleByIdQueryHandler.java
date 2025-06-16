package com.kynsof.identity.application.query.manageRole.getByid;

import com.kynsof.identity.domain.dto.ManageRolDto;
import com.kynsof.identity.domain.interfaces.service.IManageRoleService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import org.springframework.stereotype.Component;


@Component
public class FindManageRoleByIdQueryHandler implements IQueryHandler<FindManageRoleByIdQuery, ManageRoleResponse> {

    private final IManageRoleService service;

    public FindManageRoleByIdQueryHandler(IManageRoleService service) {
        this.service = service;
    }

    @Override
    public ManageRoleResponse handle(FindManageRoleByIdQuery query) {
        ManageRolDto response = service.findById(query.getId());

        return new ManageRoleResponse(response);
    }
}
