package com.kynsof.payment.domain.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.payment.domain.dto.BusinessDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IBusiness {
    void create(BusinessDto object);

    void update(BusinessDto object);

    void delete(UUID id);
    void deleteIds(List<UUID> ids);

    BusinessDto findById(UUID id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);

    List<BusinessDto> findAll();
}