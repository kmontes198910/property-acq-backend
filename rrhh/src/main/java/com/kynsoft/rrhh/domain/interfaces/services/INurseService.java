package com.kynsoft.rrhh.domain.interfaces.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.rrhh.domain.dto.NurseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface INurseService {
    void create(NurseDto object);
    void update(NurseDto object);
    void delete(NurseDto object);
    NurseDto findById(UUID id);
    NurseDto findByIdentification(String identification);
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);

}