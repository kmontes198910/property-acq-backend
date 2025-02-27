package com.kynsof.payment.domain.service;

import com.kynsof.payment.domain.dto.ClientDto;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import java.util.List;

import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface IClientService {
    UUID create(ClientDto client);
    UUID update(ClientDto client);
    void delete(UUID id);
    ClientDto findById(UUID id);
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}