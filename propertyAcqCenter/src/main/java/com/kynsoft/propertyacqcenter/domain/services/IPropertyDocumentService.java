package com.kynsoft.propertyacqcenter.domain.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDocumentDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IPropertyDocumentService {

    void create(PropertyDocumentDto dto);

    void update(PropertyDocumentDto dto);

    void delete(UUID id);

    PropertyDocumentDto findById(UUID id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}
