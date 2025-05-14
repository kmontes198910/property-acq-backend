package com.kynsoft.propertyacqcenter.application.query.ownerShipLegalEntity.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.IOwnerShipLegalEntityService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchOwnerShipLegalEntityQueryHandler implements IQueryHandler<GetSearchOwnerShipLegalEntityQuery, PaginatedResponse>{

    private final IOwnerShipLegalEntityService ownerShipLegalEntityService;

    public GetSearchOwnerShipLegalEntityQueryHandler(IOwnerShipLegalEntityService ownerShipLegalEntityService) {
        this.ownerShipLegalEntityService = ownerShipLegalEntityService;
    }

    @Override
    public PaginatedResponse handle(GetSearchOwnerShipLegalEntityQuery query) {

        return this.ownerShipLegalEntityService.search(query.getPageable(),query.getFilter());
    }
}
