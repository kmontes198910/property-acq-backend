package com.kynsoft.propertyacqcenter.domain.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.dto.ContactPersonDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IContactPersonService {

    UUID create(ContactPersonDto contactPersonDto);

    void update(ContactPersonDto contactPersonDto);

    ContactPersonDto findById(UUID id);

    void delete(UUID id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}
