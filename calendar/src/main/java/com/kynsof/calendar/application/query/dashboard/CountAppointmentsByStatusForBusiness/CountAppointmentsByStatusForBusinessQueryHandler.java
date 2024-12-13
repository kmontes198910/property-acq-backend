package com.kynsof.calendar.application.query.dashboard.CountAppointmentsByStatusForBusiness;

import com.kynsof.calendar.domain.service.IReceiptService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CountAppointmentsByStatusForBusinessQueryHandler implements IQueryHandler<CountAppointmentsByStatusForBusinessQuery, CountAppointmentsByStatusForBusinessResponse>  {

    private final IReceiptService service;

    public CountAppointmentsByStatusForBusinessQueryHandler(IReceiptService service) {
        this.service = service;
    }

    @Override
    public CountAppointmentsByStatusForBusinessResponse handle(CountAppointmentsByStatusForBusinessQuery query) {
        List<Map<String, Object>> response = service.getAppointmentsByStatus(query.getBusinessId());
        return new CountAppointmentsByStatusForBusinessResponse(response);
    }

}
