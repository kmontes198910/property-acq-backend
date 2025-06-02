package com.kynsoft.propertyacqcenter.application.query.ownerShipLegalEntity.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.OwnerShipLegalEntityResponse;
import com.kynsoft.propertyacqcenter.domain.services.IOwnerShipLegalEntityService;
import org.springframework.stereotype.Component;

@Component
public class GetByIdOwnerShipLegalEntityQueryHandler implements IQueryHandler<GetByIdOwnerShipLegalEntityQuery, OwnerShipLegalEntityResponse>{

    private final IOwnerShipLegalEntityService ownerShipLegalEntityService;

    public GetByIdOwnerShipLegalEntityQueryHandler(IOwnerShipLegalEntityService ownerShipLegalEntityService) {
        this.ownerShipLegalEntityService = ownerShipLegalEntityService;
    }

    @Override
    public OwnerShipLegalEntityResponse handle(GetByIdOwnerShipLegalEntityQuery query) {

        return new OwnerShipLegalEntityResponse(this.ownerShipLegalEntityService.findById(query.getId()));
    }
}
