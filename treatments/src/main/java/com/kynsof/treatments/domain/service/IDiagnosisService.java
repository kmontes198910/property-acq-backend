package com.kynsof.treatments.domain.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.treatments.domain.dto.DiagnosisDto;
import com.kynsof.treatments.domain.dto.ExternalConsultationDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IDiagnosisService {

    void create(DiagnosisDto diagnosisDtoList);

    void update(DiagnosisDto diagnosis);

    void delete(DiagnosisDto treatment);
    void deleteByIds(List<UUID> ids);

    DiagnosisDto findById(UUID id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);

    PaginatedResponse findByExternalConsultation(ExternalConsultationDto externalConsultation, Pageable pageable);
}
