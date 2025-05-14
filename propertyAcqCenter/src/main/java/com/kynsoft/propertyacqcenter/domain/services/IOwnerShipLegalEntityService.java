package com.kynsoft.propertyacqcenter.domain.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.dto.OwnerShipLegalEntityDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IOwnerShipLegalEntityService {

    UUID create(OwnerShipLegalEntityDto address);

    void update(OwnerShipLegalEntityDto address);

    void delete(UUID id);

    OwnerShipLegalEntityDto findById(UUID id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}
