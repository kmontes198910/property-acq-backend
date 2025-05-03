package com.kynsoft.cirugia.application.query.preoperative.getBySurgeryId;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.cirugia.domain.dto.PreOperative;
import com.kynsoft.cirugia.domain.service.IPreOperativeRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class GetPreOperativeBySurgeryIdQueryHandler implements IQueryHandler<GetPreOperativeBySurgeryIdQuery, GetPreOperativeBySurgeryIdResponse> {

    private final IPreOperativeRepository preOperativeRepository;

    public GetPreOperativeBySurgeryIdQueryHandler(IPreOperativeRepository preOperativeRepository) {
        this.preOperativeRepository = preOperativeRepository;
    }

    @Override
    public GetPreOperativeBySurgeryIdResponse handle(GetPreOperativeBySurgeryIdQuery query) {
        log.info("Retrieving PreOperative record for surgery ID: {}", query.getSurgeryId());
        
        Optional<PreOperative> preOperative = preOperativeRepository.findBySurgeryId(query.getSurgeryId());
        
        log.info("Found PreOperative record for surgery ID: {}: {}", query.getSurgeryId(), preOperative.isPresent());
        
        return new GetPreOperativeBySurgeryIdResponse(preOperative.orElse(null));
    }
}