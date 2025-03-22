package com.kynsof.treatments.domain.service;

import com.kynsof.treatments.domain.dto.InsuranceDto;

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
