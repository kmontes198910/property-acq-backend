package com.kynsof.treatments.domain.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.treatments.domain.dto.ExternalConsultationDto;
import com.kynsof.treatments.domain.dto.ResultDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IResultService {
    ResultDto save(ResultDto resultDto, ExternalConsultationDto externalConsultation);
    ResultDto update(ResultDto resultDto);
    void delete(UUID id);
    ResultDto findById(UUID id);
    List<ResultDto> findByExternalConsultationId(UUID externalConsultationId);
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filter, String query);
}