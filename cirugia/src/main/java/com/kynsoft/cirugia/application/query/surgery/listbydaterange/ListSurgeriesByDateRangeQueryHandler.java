package com.kynsoft.cirugia.application.query.surgery.listbydaterange;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.cirugia.application.query.SurgeryListResponse;
import com.kynsoft.cirugia.domain.interfaces.ISurgeryService;
import com.kynsoft.cirugia.domain.model.Surgery;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListSurgeriesByDateRangeQueryHandler implements IQueryHandler<ListSurgeriesByDateRangeQuery, SurgeryListResponse> {

    private final ISurgeryService service;

    public ListSurgeriesByDateRangeQueryHandler(ISurgeryService service) {
        this.service = service;
    }

    @Override
    public SurgeryListResponse handle(ListSurgeriesByDateRangeQuery query) {
        List<Surgery> surgeries = service.listSurgeriesByDateRange(
                query.getStartDate(),
                query.getEndDate(),
                query.getBusinessId()
        );
        return new SurgeryListResponse(surgeries);
    }
}