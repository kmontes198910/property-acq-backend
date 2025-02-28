package com.kynsof.evaluation.application.query.evaluation.search;

import com.kynsof.evaluation.domain.service.IEvaluationService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.stereotype.Component;

@Component
public class GetSearchEvaluationQueryHandler implements IQueryHandler<GetSearchEvaluationQuery, PaginatedResponse>{

    private final IEvaluationService serviceImpl;

    public GetSearchEvaluationQueryHandler(IEvaluationService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public PaginatedResponse handle(GetSearchEvaluationQuery query) {

        return this.serviceImpl.search(query.getPageable(),query.getFilter());
    }
}
