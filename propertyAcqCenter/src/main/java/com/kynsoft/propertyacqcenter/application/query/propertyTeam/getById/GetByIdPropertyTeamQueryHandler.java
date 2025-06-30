package com.kynsoft.propertyacqcenter.application.query.propertyTeam.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.PropertyTeamResponse;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyTeamService;
import org.springframework.stereotype.Component;

@Component
public class GetByIdPropertyTeamQueryHandler implements IQueryHandler<GetByIdPropertyTeamQuery, PropertyTeamResponse>{

    private final IPropertyTeamService propertyTeamService;

    public GetByIdPropertyTeamQueryHandler(IPropertyTeamService propertyTeamService) {
        this.propertyTeamService = propertyTeamService;
    }

    @Override
    public PropertyTeamResponse handle(GetByIdPropertyTeamQuery query) {

        return new PropertyTeamResponse(this.propertyTeamService.findById(query.getId()));
    }
}
