package com.kynsoft.cirugia.application.query.recoverybed.listbybusiness;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.cirugia.domain.dto.RecoveryBed;
import com.kynsoft.cirugia.domain.service.IRecoveryBedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ListRecoveryBedsByBusinessQueryHandler implements IQueryHandler<ListRecoveryBedsByBusinessQuery, RecoveryBedListResponse> {

    private final IRecoveryBedService recoveryBedService;

    @Override
    public RecoveryBedListResponse handle(ListRecoveryBedsByBusinessQuery query) {
        log.info("Listing recovery beds for business: {}", query.getBusinessId());
        
        List<RecoveryBed> recoveryBeds = recoveryBedService.findByBusinessId(query.getBusinessId());
        
        return new RecoveryBedListResponse(recoveryBeds);
    }
}