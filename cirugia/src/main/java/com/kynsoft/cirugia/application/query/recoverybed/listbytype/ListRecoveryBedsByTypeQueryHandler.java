package com.kynsoft.cirugia.application.query.recoverybed.listbytype;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.cirugia.application.query.recoverybed.listbybusiness.RecoveryBedListResponse;
import com.kynsoft.cirugia.domain.dto.RecoveryBed;
import com.kynsoft.cirugia.domain.service.IRecoveryBedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ListRecoveryBedsByTypeQueryHandler implements IQueryHandler<ListRecoveryBedsByTypeQuery, RecoveryBedListResponse> {

    private final IRecoveryBedService recoveryBedService;

    @Override
    public RecoveryBedListResponse handle(ListRecoveryBedsByTypeQuery query) {
        log.info("Listing recovery beds with type: {}", query.getType());
        
        List<RecoveryBed> recoveryBeds = recoveryBedService.findByType(query.getType());
        
        return new RecoveryBedListResponse(recoveryBeds);
    }
}