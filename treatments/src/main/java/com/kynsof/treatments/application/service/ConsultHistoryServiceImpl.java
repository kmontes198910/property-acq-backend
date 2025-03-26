package com.kynsof.treatments.application.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsof.treatments.application.query.consultHistory.ConsultHistoryResponse;
import com.kynsof.treatments.domain.service.IConsultHistoryService;
import com.kynsof.treatments.infrastructure.entity.Business;
import com.kynsof.treatments.infrastructure.entity.ConsultHistory;
import com.kynsof.treatments.infrastructure.repositories.query.ConsulHistoryReadDataJPARepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultHistoryServiceImpl implements IConsultHistoryService {

    private final ConsulHistoryReadDataJPARepository repositoryQuery;

    public ConsultHistoryServiceImpl(
                                     ConsulHistoryReadDataJPARepository repositoryQuery) {

        this.repositoryQuery = repositoryQuery;
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Business> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<ConsultHistory> data = repositoryQuery.findAll(specifications, pageable);
        return createPaginatedResponse(data);
    }

    private PaginatedResponse createPaginatedResponse(Page<ConsultHistory> data) {
        List<ConsultHistoryResponse> consultHistoryResponses = data.getContent().stream()
                .map(ConsultHistory::toAggregate)
                .map(ConsultHistoryResponse::new)
                .collect(Collectors.toList());

        return new PaginatedResponse(consultHistoryResponses, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

}