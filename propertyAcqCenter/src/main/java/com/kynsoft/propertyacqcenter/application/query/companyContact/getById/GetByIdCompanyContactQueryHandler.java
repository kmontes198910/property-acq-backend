package com.kynsoft.propertyacqcenter.application.query.companyContact.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.CompanyContactResponse;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyContactService;
import org.springframework.stereotype.Component;

@Component
public class GetByIdCompanyContactQueryHandler implements IQueryHandler<GetByIdCompanyContactQuery, CompanyContactResponse>{

    private final ICompanyContactService companyContactService;

    public GetByIdCompanyContactQueryHandler(ICompanyContactService companyContactService) {
        this.companyContactService = companyContactService;
    }

    @Override
    public CompanyContactResponse handle(GetByIdCompanyContactQuery query) {

        return new CompanyContactResponse(this.companyContactService.findById(query.getId()));
    }
}
