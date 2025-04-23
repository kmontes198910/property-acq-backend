package com.kynsoft.propertyacqcenter.application.query.companyType.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.CompanyTypeResponse;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyTypeService;
import org.springframework.stereotype.Component;

@Component
public class GetByIdCompanyTypeQueryHandler implements IQueryHandler<GetByIdCompanyTypeQuery, CompanyTypeResponse>{

    private final ICompanyTypeService companyTypeService;

    public GetByIdCompanyTypeQueryHandler(ICompanyTypeService companyTypeService) {
        this.companyTypeService = companyTypeService;
    }

    @Override
    public CompanyTypeResponse handle(GetByIdCompanyTypeQuery query) {

        return new CompanyTypeResponse(this.companyTypeService.findById(query.getId()));
    }
}
