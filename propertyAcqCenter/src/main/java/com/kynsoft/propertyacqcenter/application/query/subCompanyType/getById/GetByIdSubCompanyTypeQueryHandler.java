package com.kynsoft.propertyacqcenter.application.query.subCompanyType.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.SubCompanyTypeResponse;
import org.springframework.stereotype.Component;
import com.kynsoft.propertyacqcenter.domain.services.ISubCompanyTypeService;

@Component
public class GetByIdSubCompanyTypeQueryHandler implements IQueryHandler<GetByIdSubCompanyTypeQuery, SubCompanyTypeResponse>{

    private final ISubCompanyTypeService constructionTypeService;

    public GetByIdSubCompanyTypeQueryHandler(ISubCompanyTypeService constructionTypeService) {
        this.constructionTypeService = constructionTypeService;
    }

    @Override
    public SubCompanyTypeResponse handle(GetByIdSubCompanyTypeQuery query) {

        return new SubCompanyTypeResponse(this.constructionTypeService.findById(query.getId()));
    }
}
