package com.kynsoft.propertyacqcenter.application.query.address.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.AddressResponse;
import com.kynsoft.propertyacqcenter.domain.services.IAddressService;
import org.springframework.stereotype.Component;

@Component
public class GetByIdAddressQueryHandler implements IQueryHandler<GetByIdAddressQuery, AddressResponse>{

    private final IAddressService service;

    public GetByIdAddressQueryHandler(IAddressService service) {
        this.service = service;
    }

    @Override
    public AddressResponse handle(GetByIdAddressQuery query) {

        return new AddressResponse(this.service.findById(query.getId()));
    }
}
