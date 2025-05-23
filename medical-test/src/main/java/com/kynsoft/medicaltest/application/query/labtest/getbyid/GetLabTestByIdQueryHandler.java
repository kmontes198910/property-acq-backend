package com.kynsoft.medicaltest.application.query.labtest.getbyid;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsoft.medicaltest.domain.dto.LabTestDto;
import com.kynsoft.medicaltest.domain.service.ILabTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Manejador para la consulta de examen de laboratorio por ID
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class GetLabTestByIdQueryHandler implements IQueryHandler<GetLabTestByIdQuery, LabTestResponse> {

    private final ILabTestService labTestService;

    @Override
    public LabTestResponse handle(GetLabTestByIdQuery query) {
        log.info("Buscando examen de laboratorio con ID: {}", query.getId());
        
        LabTestDto labTest = labTestService.findById(query.getId())
                .orElseThrow(() -> new BusinessNotFoundException(
                        new GlobalBusinessException(DomainErrorMessage.BUSINESS_NOT_FOUND,
                                new ErrorField("id", "Examen de laboratorio no encontrado con ID: " + query.getId()))));
        
        return new LabTestResponse(labTest);
    }
}
