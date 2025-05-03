package com.kynsoft.cirugia.application.query.recoverybed.listbystatus;

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
public class ListRecoveryBedsByStatusQueryHandler implements IQueryHandler<ListRecoveryBedsByStatusQuery, RecoveryBedListResponse> {

    private final IRecoveryBedService recoveryBedService;

    @Override
    public RecoveryBedListResponse handle(ListRecoveryBedsByStatusQuery query) {
        log.info("Listing recovery beds with status: {}", query.getStatus());
        
        List<RecoveryBed> recoveryBeds = recoveryBedService.findByStatus(query.getStatus());
        
        return new RecoveryBedListResponse(recoveryBeds);
    }
}