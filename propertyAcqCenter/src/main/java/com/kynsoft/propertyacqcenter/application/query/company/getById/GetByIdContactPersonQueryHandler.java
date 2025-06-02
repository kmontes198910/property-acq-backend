package com.kynsoft.propertyacqcenter.application.query.company.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.CompanyResponse;
import org.springframework.stereotype.Component;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyService;

@Component
public class GetByIdContactPersonQueryHandler implements IQueryHandler<GetByIdContactPersonQuery, CompanyResponse> {

    private final ICompanyService contactPersonService;

    public GetByIdContactPersonQueryHandler(ICompanyService contactPersonService) {
        this.contactPersonService = contactPersonService;
    }

    @Override
    public CompanyResponse handle(GetByIdContactPersonQuery query) {
        return new CompanyResponse(this.contactPersonService.findById(query.getId()));
    }
}
