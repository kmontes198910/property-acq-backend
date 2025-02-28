package com.kynsof.evaluation.application.query.evaluationExamenType.getById;

import com.kynsof.evaluation.application.object.response.EvaluationExamenTypeResponse;
import com.kynsof.evaluation.domain.dto.EvaluationExamenTypeDto;
import com.kynsof.evaluation.domain.service.IEvaluationExamenTypeService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import org.springframework.stereotype.Component;

@Component
public class FindEvaluationExamentTypeByIdQueryHandler implements IQueryHandler<FindEvaluationExamentTypeByIdQuery, EvaluationExamenTypeResponse>  {

    private final IEvaluationExamenTypeService service;

    public FindEvaluationExamentTypeByIdQueryHandler(IEvaluationExamenTypeService service) {
        this.service = service;
    }

    @Override
    public EvaluationExamenTypeResponse handle(FindEvaluationExamentTypeByIdQuery query) {
        EvaluationExamenTypeDto response = service.findByIds(query.getId());

        return new EvaluationExamenTypeResponse(response);
    }
}
