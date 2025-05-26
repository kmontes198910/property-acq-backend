package com.kynsoft.propertyacqcenter.application.query.teamAssignment.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.stereotype.Component;
import com.kynsoft.propertyacqcenter.domain.services.ITeamAssignmentService;

@Component
public class GetSearchTeamAssignmentQueryHandler implements IQueryHandler<GetSearchTeamAssignmentQuery, PaginatedResponse>{

    private final ITeamAssignmentService teamAssignmentService;

    public GetSearchTeamAssignmentQueryHandler(ITeamAssignmentService teamAssignmentService) {
        this.teamAssignmentService = teamAssignmentService;
    }

    @Override
    public PaginatedResponse handle(GetSearchTeamAssignmentQuery query) {

        return this.teamAssignmentService.search(query.getPageable(),query.getFilter());
    }
}
