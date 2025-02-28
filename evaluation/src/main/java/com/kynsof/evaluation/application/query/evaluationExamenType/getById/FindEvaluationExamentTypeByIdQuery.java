package com.kynsof.evaluation.application.query.evaluationExamenType.getById;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;

import java.util.UUID;

@Getter
public class FindEvaluationExamentTypeByIdQuery  implements IQuery {

    private UUID id;

    public FindEvaluationExamentTypeByIdQuery(UUID id) {
        this.id = id;
    }

}
