package com.kynsoft.cirugia.application.query.intraoperative.getBySurgeryId;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.cirugia.domain.dto.IntraOperative;
import com.kynsoft.cirugia.domain.service.IIntraOperativeRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class GetIntraOperativeBySurgeryIdQueryHandler implements IQueryHandler<GetIntraOperativeBySurgeryIdQuery, GetIntraOperativeBySurgeryIdResponse> {

    private final IIntraOperativeRepository intraOperativeRepository;

    public GetIntraOperativeBySurgeryIdQueryHandler(IIntraOperativeRepository intraOperativeRepository) {
        this.intraOperativeRepository = intraOperativeRepository;
    }

    @Override
    public GetIntraOperativeBySurgeryIdResponse handle(GetIntraOperativeBySurgeryIdQuery query) {
        log.info("Retrieving IntraOperative records for surgery ID: {}", query.getSurgeryId());
        
        List<IntraOperative> intraOperatives = intraOperativeRepository.findBySurgeryId(query.getSurgeryId());
        if (intraOperatives.isEmpty()) {
            log.info("No IntraOperative records found for surgery ID: {}", query.getSurgeryId());
            return new GetIntraOperativeBySurgeryIdResponse();
        }
        
        log.info("Found {} IntraOperative records for surgery ID: {}", intraOperatives.size(), query.getSurgeryId());
        
        return new GetIntraOperativeBySurgeryIdResponse(intraOperatives.get(0));
    }
}