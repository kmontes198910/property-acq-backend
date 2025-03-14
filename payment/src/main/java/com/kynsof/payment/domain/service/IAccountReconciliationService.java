package com.kynsof.payment.domain.service;

import com.kynsof.payment.domain.dto.AccountReconciliationDto;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IAccountReconciliationService {

    void create(AccountReconciliationDto dto);

    void update(AccountReconciliationDto billingDto);

    AccountReconciliationDto findById(UUID id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);

    void delete(AccountReconciliationDto object);
}
