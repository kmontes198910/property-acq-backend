package com.kynsoft.cirugia.application.query.diagnosis.listbysurgery;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.cirugia.domain.service.IDiagnosisService;
import com.kynsoft.cirugia.domain.dto.Diagnosis;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ListDiagnosesBySurgeryQueryHandler implements IQueryHandler<ListDiagnosesBySurgeryQuery, DiagnosisListResponse> {

    private final IDiagnosisService diagnosisService;

    @Override
    @Transactional(readOnly = true)
    public DiagnosisListResponse handle(ListDiagnosesBySurgeryQuery query) {
        log.info("Listing diagnoses for surgery ID: {}", query.getSurgeryId());
        
        List<Diagnosis> diagnoses = diagnosisService.findBySurgeryId(query.getSurgeryId());
        
        return new DiagnosisListResponse(diagnoses);
    }
}