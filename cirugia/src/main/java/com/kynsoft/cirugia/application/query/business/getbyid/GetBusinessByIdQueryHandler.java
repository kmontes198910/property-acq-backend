package com.kynsoft.cirugia.application.query.business.getbyid;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.cirugia.application.response.BusinessResponse;
import com.kynsoft.cirugia.domain.dto.BusinessDto;
import com.kynsoft.cirugia.domain.service.IBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GetBusinessByIdQueryHandler implements IQueryHandler<GetBusinessByIdQuery, BusinessResponse> {

    private final IBusinessService service;

    public GetBusinessByIdQueryHandler(IBusinessService service) {
        this.service = service;
    }

    @Override
    public BusinessResponse handle(GetBusinessByIdQuery query) {
        BusinessDto business = service.findById(query.getId());

        return new BusinessResponse(business);
    }
}