package com.kynsof.evaluation.domain.service;

import com.kynsof.evaluation.domain.dto.EvaluationExamenTypeDto;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IEvaluationExamenTypeService {
    void create(EvaluationExamenTypeDto object);
    void update(EvaluationExamenTypeDto object);
    void delete(UUID id);
    EvaluationExamenTypeDto findByIds(UUID id);
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}