package com.kynsof.treatments.application.query.optometryExam.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.treatments.domain.service.IOptometryExamService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchOptometryExamQueryHandler implements IQueryHandler<GetSearchOptometryExamQuery, PaginatedResponse>{

    private final IOptometryExamService serviceImpl;

    public GetSearchOptometryExamQueryHandler(IOptometryExamService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public PaginatedResponse handle(GetSearchOptometryExamQuery query) {

        return this.serviceImpl.search(query.getPageable(),query.getFilter());
    }
}
