package com.kynsoft.propertyacqcenter.application.query.constructionType.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.ConstructionTypeResponse;
import com.kynsoft.propertyacqcenter.domain.services.IConstructionTypeService;
import org.springframework.stereotype.Component;

@Component
public class GetByIdConstructionTypeQueryHandler implements IQueryHandler<GetByIdConstructionTypeQuery, ConstructionTypeResponse>{

    private final IConstructionTypeService constructionTypeService;

    public GetByIdConstructionTypeQueryHandler(IConstructionTypeService constructionTypeService) {
        this.constructionTypeService = constructionTypeService;
    }

    @Override
    public ConstructionTypeResponse handle(GetByIdConstructionTypeQuery query) {

        return new ConstructionTypeResponse(this.constructionTypeService.findById(query.getId()));
    }
}
