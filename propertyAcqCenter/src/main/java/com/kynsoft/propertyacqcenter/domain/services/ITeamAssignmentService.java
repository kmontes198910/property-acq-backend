package com.kynsoft.propertyacqcenter.domain.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.dto.TeamAssignmentDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ITeamAssignmentService {

    UUID create(TeamAssignmentDto dto);

    void update(TeamAssignmentDto dto);

    void delete(UUID id);

    TeamAssignmentDto findById(UUID id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);

    TeamAssignmentDto findByPropertyId(String propertyId);
}
