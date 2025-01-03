package com.kynsof.treatments.domain.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.treatments.domain.dto.BusinessProcedureDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IBusinessProcedureService {
    void create(List<BusinessProcedureDto> object);

    void update(BusinessProcedureDto object);

    void delete(UUID id);
    void deleteIds(List<UUID> ids);
    BusinessProcedureDto findById(UUID id);

    List<BusinessProcedureDto> findByCodes(UUID businessId, List<String> codes);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);

}