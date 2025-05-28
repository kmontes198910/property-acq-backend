package com.kynsoft.propertyacqcenter.application.query.acquisitionDetails.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.AcquisitionDetailsResponse;
import com.kynsoft.propertyacqcenter.domain.services.IAcquisitionDetailsService;
import org.springframework.stereotype.Component;

@Component
public class GetByIdAcquisitionDetailsQueryHandler implements IQueryHandler<GetByIdAcquisitionDetailsQuery, AcquisitionDetailsResponse>{

    private final IAcquisitionDetailsService acquisitionDetailsService;

    public GetByIdAcquisitionDetailsQueryHandler(IAcquisitionDetailsService acquisitionDetailsService) {
        this.acquisitionDetailsService = acquisitionDetailsService;
    }

    @Override
    public AcquisitionDetailsResponse handle(GetByIdAcquisitionDetailsQuery query) {

        return new AcquisitionDetailsResponse(this.acquisitionDetailsService.findById(query.getId()));
    }
}
