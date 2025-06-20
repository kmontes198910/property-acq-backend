package com.kynsoft.propertyacqcenter.application.query.manageRole.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.IManageRoleService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchManageRoleQueryHandler implements IQueryHandler<GetSearchManageRoleQuery, PaginatedResponse>{

    private final IManageRoleService manageRoleService;

    public GetSearchManageRoleQueryHandler(IManageRoleService manageRoleService) {
        this.manageRoleService = manageRoleService;
    }

    @Override
    public PaginatedResponse handle(GetSearchManageRoleQuery query) {

        return this.manageRoleService.search(query.getPageable(),query.getFilter());
    }
}
