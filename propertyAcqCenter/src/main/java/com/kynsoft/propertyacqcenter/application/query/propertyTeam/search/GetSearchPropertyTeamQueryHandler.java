package com.kynsoft.propertyacqcenter.application.query.propertyTeam.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyTeamService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchPropertyTeamQueryHandler implements IQueryHandler<GetSearchPropertyTeamQuery, PaginatedResponse>{

    private final IPropertyTeamService propertyTeamService;

    public GetSearchPropertyTeamQueryHandler(IPropertyTeamService propertyTeamService) {
        this.propertyTeamService = propertyTeamService;
    }

    @Override
    public PaginatedResponse handle(GetSearchPropertyTeamQuery query) {

        return this.propertyTeamService.search(query.getPageable(),query.getFilter());
    }
}
