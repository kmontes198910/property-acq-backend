package com.kynsoft.medicaltest.application.query.labTestRequest.getbyid;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.medicaltest.domain.dto.LabTestRequestDto;
import com.kynsoft.medicaltest.domain.service.ILabTestRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetLabTestRequestByIdQueryHandler implements IQueryHandler<GetLabTestRequestByIdQuery, LabTestRequestResponse> {

    private final ILabTestRequestService labTestRequestService;

    @Override
    public LabTestRequestResponse handle(GetLabTestRequestByIdQuery query) {
        log.info("Buscando examen de laboratorio con ID: {}", query.getId());

        LabTestRequestDto response = labTestRequestService.findById(query.getId());

        return new LabTestRequestResponse(response);
    }
}
