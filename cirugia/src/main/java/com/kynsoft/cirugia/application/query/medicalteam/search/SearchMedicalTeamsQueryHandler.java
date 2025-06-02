package com.kynsoft.cirugia.application.query.medicalteam.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.cirugia.domain.service.IMedicalTeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchMedicalTeamsQueryHandler implements IQueryHandler<SearchMedicalTeamsQuery, PaginatedResponse> {

    private final IMedicalTeamService medicalTeamService;

    @Override
    public PaginatedResponse handle(SearchMedicalTeamsQuery query) {
        log.info("Handling search medical teams query with filters: {}", query.getFilters());

        return medicalTeamService.search(query.getPageable(), query.getFilters());
    }
}