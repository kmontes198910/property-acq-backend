package com.kynsoft.propertyacqcenter.application.query.teamAssignment.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.TeamAssignmentResponse;
import org.springframework.stereotype.Component;
import com.kynsoft.propertyacqcenter.domain.services.ITeamAssignmentService;

@Component
public class GetByIdTeamAssignmentQueryHandler implements IQueryHandler<GetByIdTeamAssignmentQuery, TeamAssignmentResponse>{

    private final ITeamAssignmentService teamAssignmentService;

    public GetByIdTeamAssignmentQueryHandler(ITeamAssignmentService teamAssignmentService) {
        this.teamAssignmentService = teamAssignmentService;
    }

    @Override
    public TeamAssignmentResponse handle(GetByIdTeamAssignmentQuery query) {

        return new TeamAssignmentResponse(this.teamAssignmentService.findById(query.getId()));
    }
}
