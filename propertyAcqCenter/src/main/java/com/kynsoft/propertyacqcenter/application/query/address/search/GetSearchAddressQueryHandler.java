package com.kynsoft.propertyacqcenter.application.query.address.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.IAddressService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchAddressQueryHandler implements IQueryHandler<GetSearchAddressQuery, PaginatedResponse>{

    private final IAddressService addressService;

    public GetSearchAddressQueryHandler(IAddressService addressService) {
        this.addressService = addressService;
    }

    @Override
    public PaginatedResponse handle(GetSearchAddressQuery query) {

        return this.addressService.search(query.getPageable(),query.getFilter());
    }
}
