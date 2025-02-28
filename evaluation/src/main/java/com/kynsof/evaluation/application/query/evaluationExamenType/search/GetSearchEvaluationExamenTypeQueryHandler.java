package com.kynsof.evaluation.application.query.evaluationExamenType.search;

import com.kynsof.evaluation.domain.service.IEvaluationExamenTypeService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.stereotype.Component;

@Component
public class GetSearchEvaluationExamenTypeQueryHandler implements IQueryHandler<GetSearchEvaluationExamenTypeQuery, PaginatedResponse>{

    private final IEvaluationExamenTypeService serviceImpl;

    public GetSearchEvaluationExamenTypeQueryHandler(IEvaluationExamenTypeService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public PaginatedResponse handle(GetSearchEvaluationExamenTypeQuery query) {

        return this.serviceImpl.search(query.getPageable(),query.getFilter());
    }
}
