package com.kynsof.evaluation.application.query.patients.search;

import com.kynsof.evaluation.domain.service.IPatientsService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.stereotype.Component;

@Component
public class GetSearchPatientsQueryHandler implements IQueryHandler<GetSearchPatientsQuery, PaginatedResponse>{

    private final IPatientsService serviceImpl;

    public GetSearchPatientsQueryHandler(IPatientsService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public PaginatedResponse handle(GetSearchPatientsQuery query) {

        return this.serviceImpl.search(query.getPageable(),query.getFilter());
    }
}
