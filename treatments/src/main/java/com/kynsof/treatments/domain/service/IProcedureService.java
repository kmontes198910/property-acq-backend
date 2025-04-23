package com.kynsof.treatments.domain.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.treatments.domain.dto.ProcedureDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IProcedureService {

    void create(ProcedureDto patients);

    void update(ProcedureDto patients);

    void delete(ProcedureDto id);

    ProcedureDto findById(UUID code);

    ProcedureDto findByCode(String code);
    
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);

    Long countByCodeAndNotId(String code, UUID id);

    Long countByNameAndNotId(String name, UUID id);
}
