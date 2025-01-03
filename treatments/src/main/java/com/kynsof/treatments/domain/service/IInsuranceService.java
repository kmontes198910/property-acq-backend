package com.kynsof.treatments.domain.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.treatments.domain.dto.InsuranceDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IInsuranceService {

    void create(InsuranceDto insurance);

    void update(InsuranceDto insurance);

    void delete(UUID id);

    InsuranceDto findById(UUID id);

//    PaginatedResponse findAll(Pageable pageable);
//
//    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}
