package com.kynsoft.propertyacqcenter.domain.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ICompanyService {

    UUID create(CompanyDto contactPersonDto);

    void update(CompanyDto contactPersonDto);

    CompanyDto findById(UUID id);

    void delete(UUID id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}
