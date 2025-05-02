package com.kynsoft.cirugia.application.query.recoverybed.listavailable;

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
public class ListAvailableRecoveryBedsQueryHandler implements IQueryHandler<ListAvailableRecoveryBedsQuery, RecoveryBedListResponse> {

    private final IRecoveryBedService recoveryBedService;

    @Override
    public RecoveryBedListResponse handle(ListAvailableRecoveryBedsQuery query) {
        log.info("Listing available recovery beds for business: {}", query.getBusinessId());
        
        List<RecoveryBed> recoveryBeds = recoveryBedService.findAvailableBeds(query.getBusinessId());
        
        return new RecoveryBedListResponse(recoveryBeds);
    }
}