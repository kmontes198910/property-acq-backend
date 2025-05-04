package com.kynsoft.propertyacqcenter.application.query.companyAddress.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.CompanyAddressResponse;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyAddressService;
import org.springframework.stereotype.Component;

@Component
public class GetByIdCompanyAddressQueryHandler implements IQueryHandler<GetByIdCompanyAddressQuery, CompanyAddressResponse>{

    private final ICompanyAddressService companyAddressService;

    public GetByIdCompanyAddressQueryHandler(ICompanyAddressService companyAddressService) {
        this.companyAddressService = companyAddressService;
    }

    @Override
    public CompanyAddressResponse handle(GetByIdCompanyAddressQuery query) {

        return new CompanyAddressResponse(this.companyAddressService.findById(query.getId()));
    }
}
