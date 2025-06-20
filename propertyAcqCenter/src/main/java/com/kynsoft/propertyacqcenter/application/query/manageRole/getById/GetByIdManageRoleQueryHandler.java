package com.kynsoft.propertyacqcenter.application.query.manageRole.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.ManageRoleResponse;
import com.kynsoft.propertyacqcenter.domain.services.IManageRoleService;
import org.springframework.stereotype.Component;

@Component
public class GetByIdManageRoleQueryHandler implements IQueryHandler<GetByIdManageRoleQuery, ManageRoleResponse>{

    private final IManageRoleService service;

    public GetByIdManageRoleQueryHandler(IManageRoleService service) {
        this.service = service;
    }

    @Override
    public ManageRoleResponse handle(GetByIdManageRoleQuery query) {

        return new ManageRoleResponse(this.service.findById(query.getId()));
    }
}
