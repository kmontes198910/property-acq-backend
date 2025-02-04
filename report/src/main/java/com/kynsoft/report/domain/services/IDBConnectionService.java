package com.kynsoft.report.domain.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.report.domain.dto.DBConnectionDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IDBConnectionService {

    void create(DBConnectionDto object);

    void update(DBConnectionDto object);

    void delete(DBConnectionDto object);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);

    DBConnectionDto findById(UUID id);

    Long countByCodeAndNotId(String code, UUID id);
}
