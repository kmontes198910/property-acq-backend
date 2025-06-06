package com.kynsoft.settings.application.query.address.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.settings.application.response.AddressResponse;
import com.kynsoft.settings.domain.services.IAddressService;
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
