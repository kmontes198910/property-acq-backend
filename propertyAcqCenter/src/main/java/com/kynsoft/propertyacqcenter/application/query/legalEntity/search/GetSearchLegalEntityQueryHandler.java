package com.kynsoft.propertyacqcenter.application.query.legalEntity.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchLegalEntityQueryHandler implements IQueryHandler<GetSearchLegalEntityQuery, PaginatedResponse>{

    private final ILegalEntityService legalEntityService;

    public GetSearchLegalEntityQueryHandler(ILegalEntityService legalEntityService) {
        this.legalEntityService = legalEntityService;
    }

    @Override
    public PaginatedResponse handle(GetSearchLegalEntityQuery query) {

        return this.legalEntityService.search(query.getPageable(),query.getFilter());
    }
}
