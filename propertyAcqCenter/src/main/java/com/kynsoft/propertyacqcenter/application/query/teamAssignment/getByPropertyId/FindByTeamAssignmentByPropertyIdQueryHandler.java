package com.kynsoft.propertyacqcenter.application.query.teamAssignment.getByPropertyId;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.TeamAssignmentResponse;
import com.kynsoft.propertyacqcenter.domain.services.ITeamAssignmentService;
import org.springframework.stereotype.Component;

@Component
public class FindByTeamAssignmentByPropertyIdQueryHandler implements IQueryHandler<FindByTeamAssignmentByPropertyIdQuery, TeamAssignmentResponse> {

    private final ITeamAssignmentService teamAssignmentService;

    public FindByTeamAssignmentByPropertyIdQueryHandler(ITeamAssignmentService teamAssignmentService) {
        this.teamAssignmentService = teamAssignmentService;
    }

    @Override
    public TeamAssignmentResponse handle(FindByTeamAssignmentByPropertyIdQuery query) {
        return new TeamAssignmentResponse(this.teamAssignmentService.findByPropertyId(query.getId()));
    }
}
