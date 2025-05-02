package com.kynsoft.cirugia.application.query.diagnosis.getbyid;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsoft.cirugia.domain.service.IDiagnosisService;
import com.kynsoft.cirugia.domain.dto.Diagnosis;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetDiagnosisByIdQueryHandler implements IQueryHandler<GetDiagnosisByIdQuery, DiagnosisResponse> {

    private final IDiagnosisService diagnosisService;

    @Override
    @Transactional(readOnly = true)
    public DiagnosisResponse handle(GetDiagnosisByIdQuery query) {
        log.info("Finding diagnosis with ID: {}", query.getId());
        
        Optional<Diagnosis> diagnosisOptional = diagnosisService.findById(query.getId());
        
        if (diagnosisOptional.isEmpty()) {
            throw new BusinessNotFoundException(
                new GlobalBusinessException(DomainErrorMessage.BUSINESS_NOT_FOUND, 
                new ErrorField("id", "Diagnosis not found with ID: " + query.getId())));
        }
        
        return new DiagnosisResponse(diagnosisOptional.get());
    }
}