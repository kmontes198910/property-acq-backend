package com.kynsoft.propertyacqcenter.application.query.companyAddress.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyAddressService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchCompanyAddressQueryHandler implements IQueryHandler<GetSearchCompanyAddressQuery, PaginatedResponse>{

    private final ICompanyAddressService companyAddressService;

    public GetSearchCompanyAddressQueryHandler(ICompanyAddressService companyAddressService) {
        this.companyAddressService = companyAddressService;
    }

    @Override
    public PaginatedResponse handle(GetSearchCompanyAddressQuery query) {

        return this.companyAddressService.search(query.getPageable(),query.getFilter());
    }
}
