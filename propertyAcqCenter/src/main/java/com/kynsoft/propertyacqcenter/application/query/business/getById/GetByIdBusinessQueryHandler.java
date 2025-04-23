package com.kynsoft.propertyacqcenter.application.query.business.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.BusinessResponse;
import com.kynsoft.propertyacqcenter.domain.services.IBusinessService;
import org.springframework.stereotype.Component;

@Component
public class GetByIdBusinessQueryHandler implements IQueryHandler<GetByIdBusinessQuery, BusinessResponse>{

    private final IBusinessService service;

    public GetByIdBusinessQueryHandler(IBusinessService service) {
        this.service = service;
    }

    @Override
    public BusinessResponse handle(GetByIdBusinessQuery query) {

        return new BusinessResponse(this.service.findById(query.getId()));
    }
}
