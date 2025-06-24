package com.kynsoft.propertyacqcenter.application.query.titleCompany.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.TitleCompanyResponse;
import org.springframework.stereotype.Component;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyService;
import org.springframework.beans.factory.annotation.Qualifier;

@Component
public class GetByIdTitleCompanyQueryHandler implements IQueryHandler<GetByIdTitleCompanyQuery, TitleCompanyResponse> {

    private final ICompanyService companyService;

    public GetByIdTitleCompanyQueryHandler(@Qualifier("titleCompanyService") ICompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    public TitleCompanyResponse handle(GetByIdTitleCompanyQuery query) {
        return new TitleCompanyResponse(this.companyService.findById(query.getId()));
    }
}
