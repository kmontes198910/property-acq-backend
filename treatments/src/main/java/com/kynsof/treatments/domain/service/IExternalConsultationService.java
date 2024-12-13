package com.kynsof.treatments.domain.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.treatments.domain.dto.ExternalConsultationDto;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface IExternalConsultationService {

    UUID create(ExternalConsultationDto externalConsultation);

    UUID createAll(ExternalConsultationDto externalConsultation);

    UUID update(ExternalConsultationDto externalConsultation);

    void delete(ExternalConsultationDto dto);

    ExternalConsultationDto findById(UUID id);

    PaginatedResponse findAll(Pageable pageable, UUID doctorId, UUID patientId);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filter);

    Long countConsultationsByBusinessAndDateRange(UUID businessId, Date startDate, Date endDate);
    List<Long> getConsultationsCountByMonth(UUID businessId,int year);
}
