package com.kynsof.evaluation.application.query.getExamByEvaluationIdAndType.getById;

import com.kynsof.evaluation.domain.dto.enumDto.EvaluationExamenType;
import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class getExamByEvaluationIdAndTypeQuery implements IQuery {
    private UUID evaluationId;
    private EvaluationExamenType type;
}
