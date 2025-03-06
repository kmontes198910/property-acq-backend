package com.kynsof.evaluation.domain.service;

import com.kynsof.evaluation.domain.dto.EvaluationPatientExamDto;
import com.kynsof.evaluation.domain.dto.enumDto.EvaluationExamenType;
import com.kynsof.evaluation.infrastructure.entity.EvaluationPatientExam;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IEvaluationPatientService {
    void create(EvaluationPatientExamDto object, List<String> questionCodes);
    void update(EvaluationPatientExamDto object);
    void delete(UUID id);
    EvaluationPatientExamDto findByIds(UUID id);
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);

    EvaluationPatientExam getExamByEvaluationIdAndType(UUID evaluationId, EvaluationExamenType examenType);
}