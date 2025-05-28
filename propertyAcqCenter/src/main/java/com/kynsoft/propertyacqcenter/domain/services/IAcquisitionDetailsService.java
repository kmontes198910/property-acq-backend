package com.kynsoft.propertyacqcenter.domain.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.dto.AcquisitionDetailsDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IAcquisitionDetailsService {

    UUID create(AcquisitionDetailsDto dto);

    void update(AcquisitionDetailsDto dto);

    void delete(UUID id);

    AcquisitionDetailsDto findById(UUID id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}
