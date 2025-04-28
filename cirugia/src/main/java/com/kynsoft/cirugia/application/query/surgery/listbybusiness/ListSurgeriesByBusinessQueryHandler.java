package com.kynsoft.cirugia.application.query.surgery.listbybusiness;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.cirugia.application.query.SurgeryListResponse;
import com.kynsoft.cirugia.domain.interfaces.ISurgeryService;
import com.kynsoft.cirugia.domain.model.Surgery;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListSurgeriesByBusinessQueryHandler implements IQueryHandler<ListSurgeriesByBusinessQuery, SurgeryListResponse> {

    private final ISurgeryService service;

    public ListSurgeriesByBusinessQueryHandler(ISurgeryService service) {
        this.service = service;
    }

    @Override
    public SurgeryListResponse handle(ListSurgeriesByBusinessQuery query) {
        List<Surgery> surgeries = service.listSurgeriesByBusiness(query.getBusinessId());
        return new SurgeryListResponse(surgeries);
    }
}