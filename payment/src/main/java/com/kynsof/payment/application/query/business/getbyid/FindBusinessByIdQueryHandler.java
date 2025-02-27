package com.kynsof.payment.application.query.business.getbyid;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.payment.application.query.business.search.BusinessResponse;
import com.kynsof.payment.domain.dto.BusinessDto;
import com.kynsof.payment.domain.service.IBusiness;
import org.springframework.stereotype.Component;

@Component
public class FindBusinessByIdQueryHandler implements IQueryHandler<FindBusinessByIdQuery, BusinessResponse>  {

    private final IBusiness service;

    public FindBusinessByIdQueryHandler(IBusiness service) {
        this.service = service;
    }

    @Override
    public BusinessResponse handle(FindBusinessByIdQuery query) {
        BusinessDto response = service.findById(query.getId());

        return new BusinessResponse(response);
    }
}
