package com.kynsof.identity.application.query.manageRole.search;

import com.kynsof.identity.domain.interfaces.service.IManageRoleService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.stereotype.Component;

@Component
public class GetSearchManageRoleQueryHandler implements IQueryHandler<GetSearchManageRoleQuery, PaginatedResponse> {

    private final IManageRoleService service;

    public GetSearchManageRoleQueryHandler(IManageRoleService service) {
        this.service = service;
    }

    @Override
    public PaginatedResponse handle(GetSearchManageRoleQuery query) {
        return this.service.search(query.getPageable(),query.getFilter());
    }
}
