package com.kynsoft.cirugia.application.query.vitalsigns.getbyid;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.cirugia.domain.dto.VitalSigns;
import com.kynsoft.cirugia.domain.service.IVitalSignsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetVitalSignsByIdQueryHandler implements IQueryHandler<GetVitalSignsByIdQuery, VitalSignsResponse> {

    private final IVitalSignsService vitalSignsService;

    @Override
    public VitalSignsResponse handle(GetVitalSignsByIdQuery query) {
        log.info("Fetching vital signs with ID: {}", query.getId());
        
        Optional<VitalSigns> vitalSignsOpt = vitalSignsService.findById(query.getId());
        
        if (vitalSignsOpt.isEmpty()) {
            throw new RuntimeException("Vital signs not found with ID: " + query.getId());
        }
        
        return new VitalSignsResponse(vitalSignsOpt.get());
    }
}