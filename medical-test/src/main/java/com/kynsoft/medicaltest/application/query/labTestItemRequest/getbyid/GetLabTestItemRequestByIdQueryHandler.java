package com.kynsoft.medicaltest.application.query.labTestItemRequest.getbyid;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.medicaltest.domain.dto.LabTestItemRequestDto;
import com.kynsoft.medicaltest.domain.service.ILabTestItemRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetLabTestItemRequestByIdQueryHandler implements IQueryHandler<GetLabTestItemRequestByIdQuery, LabTestItemRequestResponse> {

    private final ILabTestItemRequestService labTestRequestService;

    @Override
    public LabTestItemRequestResponse handle(GetLabTestItemRequestByIdQuery query) {
        LabTestItemRequestDto response = labTestRequestService.findById(query.getId());

        return new LabTestItemRequestResponse(response);
    }
}
