package com.kynsoft.cirugia.application.query.surgery.listbypatient;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.cirugia.application.query.SurgeryListResponse;
import com.kynsoft.cirugia.domain.interfaces.ISurgeryService;
import com.kynsoft.cirugia.domain.model.Surgery;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListSurgeriesByPatientQueryHandler implements IQueryHandler<ListSurgeriesByPatientQuery, SurgeryListResponse> {

    private final ISurgeryService service;

    public ListSurgeriesByPatientQueryHandler(ISurgeryService service) {
        this.service = service;
    }

    @Override
    public SurgeryListResponse handle(ListSurgeriesByPatientQuery query) {
        List<Surgery> surgeries = service.listSurgeriesByPatient(query.getPatientId());
        return new SurgeryListResponse(surgeries);
    }
}