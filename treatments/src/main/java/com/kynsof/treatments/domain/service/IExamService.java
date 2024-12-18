package com.kynsof.treatments.domain.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.treatments.domain.dto.ExamDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IExamService {

    UUID create(ExamDto exam);

    void update(ExamDto exam);

    void delete(ExamDto examDto);


    ExamDto findById(UUID id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}
