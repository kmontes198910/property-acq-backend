package com.kynsoft.settings.application.query.recoverybed.getbyid;


import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.settings.domain.dto.RecoveryBed;
import com.kynsoft.settings.domain.services.IRecoveryBedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetRecoveryBedByIdQueryHandler implements IQueryHandler<GetRecoveryBedByIdQuery, RecoveryBedResponse> {

    private final IRecoveryBedService recoveryBedService;

    @Override
    public RecoveryBedResponse handle(GetRecoveryBedByIdQuery query) {
        log.info("Fetching recovery bed with ID: {}", query.getId());
        RecoveryBed recoveryBed = recoveryBedService.findById(query.getId());
        return new RecoveryBedResponse(recoveryBed);
    }
}