package com.kynsoft.cirugia.application.query.surgery.getbyid;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.cirugia.application.query.SurgeryResponse;
import com.kynsoft.cirugia.domain.service.ISurgeryService;
import com.kynsoft.cirugia.domain.dto.Surgery;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetSurgeryByIdQueryHandler implements IQueryHandler<GetSurgeryByIdQuery, SurgeryResponse> {

    private final ISurgeryService service;

    public GetSurgeryByIdQueryHandler(ISurgeryService service) {
        this.service = service;
    }

    @Override
    public SurgeryResponse handle(GetSurgeryByIdQuery query) {
        Optional<Surgery> surgery = service.getSurgeryById(query.getId());
        
        if (surgery.isEmpty()) {
            throw new RuntimeException("Surgery not found with ID: " + query.getId());
        }
        
        return new SurgeryResponse(surgery.get());
    }
}