package com.kynsoft.propertyacqcenter.domain.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.dto.LienDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ILienService {

    UUID create(LienDto lien);

    void update(LienDto lien);

    void delete(UUID id);

    LienDto findById(UUID id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}
