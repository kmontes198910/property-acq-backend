package com.kynsoft.propertyacqcenter.domain.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyUploadDocumentDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IPropertyUploadDocumentService {

    UUID create(PropertyUploadDocumentDto address);

    void update(PropertyUploadDocumentDto address);

    void delete(UUID id);

    PropertyUploadDocumentDto findById(UUID id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}
