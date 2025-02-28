package com.kynsof.evaluation.domain.service;

import com.kynsof.evaluation.domain.dto.EvaluationDto;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IEvaluationService {
    void create(EvaluationDto object);
    void update(EvaluationDto object);
    void delete(UUID id);
    EvaluationDto findByIds(UUID id);
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}