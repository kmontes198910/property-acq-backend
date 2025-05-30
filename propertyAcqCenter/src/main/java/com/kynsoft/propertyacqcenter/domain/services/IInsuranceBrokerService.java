package com.kynsoft.propertyacqcenter.domain.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.dto.InsuranceBrokerDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IInsuranceBrokerService {

    UUID create(InsuranceBrokerDto dto);

    void update(InsuranceBrokerDto dto);

    void delete(UUID id);

    InsuranceBrokerDto findById(UUID id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}
