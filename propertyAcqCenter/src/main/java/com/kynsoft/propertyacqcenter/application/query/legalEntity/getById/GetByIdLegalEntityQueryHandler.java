package com.kynsoft.propertyacqcenter.application.query.legalEntity.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.LegalEntityFindByIdResponse;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import org.springframework.stereotype.Component;

@Component
public class GetByIdLegalEntityQueryHandler implements IQueryHandler<GetByIdLegalEntityQuery, LegalEntityFindByIdResponse>{

    private final ILegalEntityService legalEntityService;

    public GetByIdLegalEntityQueryHandler(ILegalEntityService legalEntityService) {
        this.legalEntityService = legalEntityService;
    }

    @Override
    public LegalEntityFindByIdResponse handle(GetByIdLegalEntityQuery query) {

        return new LegalEntityFindByIdResponse(this.legalEntityService.findById(query.getId()));
    }
}
