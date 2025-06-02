package com.kynsoft.rrhh.application.query.nurse.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.rrhh.domain.interfaces.services.INurseService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchNurseQueryHandler implements IQueryHandler<GetSearchNurseQuery, PaginatedResponse> {

    private final INurseService service;

    public GetSearchNurseQueryHandler(INurseService service) {
        this.service = service;
    }

    @Override
    public PaginatedResponse handle(GetSearchNurseQuery query) {
        return service.search(query.getPageable(), query.getFilterCriteria());
    }
}