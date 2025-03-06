package com.kynsof.evaluation.application.query.evaluation.getById;

import com.kynsof.evaluation.application.object.response.EvaluationResponse;
import com.kynsof.evaluation.domain.service.IEvaluationService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.stereotype.Component;

@Component
public class GetByIdEvaluationQueryHandler implements IQueryHandler<GetByIdEvaluationQuery, EvaluationResponse>{

    private final IEvaluationService serviceImpl;

    public GetByIdEvaluationQueryHandler(IEvaluationService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public EvaluationResponse handle(GetByIdEvaluationQuery query) {

        return new EvaluationResponse(this.serviceImpl.findByIds(query.getId()));
    }
}
