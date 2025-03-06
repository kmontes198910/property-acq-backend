package com.kynsof.evaluation.application.query.getExamByEvaluationIdAndType.getById;

import com.kynsof.evaluation.application.object.response.EvaluationResponse;
import com.kynsof.evaluation.domain.service.IEvaluationPatientService;
import com.kynsof.evaluation.domain.service.IEvaluationService;
import com.kynsof.evaluation.infrastructure.entity.EvaluationPatientExam;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import org.springframework.stereotype.Component;

@Component
public class getExamByEvaluationIdAndTypeQueryHandler implements IQueryHandler<getExamByEvaluationIdAndTypeQuery, EvaluationPatientExamResponse>{

    private final IEvaluationPatientService serviceImpl;

    public getExamByEvaluationIdAndTypeQueryHandler(IEvaluationPatientService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public EvaluationPatientExamResponse handle(getExamByEvaluationIdAndTypeQuery query) {

        EvaluationPatientExam evaluationPatientExam = this.serviceImpl.getExamByEvaluationIdAndType(query.getEvaluationId(), query.getType());
        if(evaluationPatientExam == null) {
            return null;
        }
        return new EvaluationPatientExamResponse(evaluationPatientExam);
    }
}
