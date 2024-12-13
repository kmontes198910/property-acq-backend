package com.kynsof.treatments.application.query.reports.contConsultByYears;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.treatments.domain.service.IExternalConsultationService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContConsultByYearsQueryHandler implements IQueryHandler<ContConsultByYearsQuery, ContConsultByYearsResponse> {

    private final IExternalConsultationService serviceImpl;

    public ContConsultByYearsQueryHandler(IExternalConsultationService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public ContConsultByYearsResponse handle(ContConsultByYearsQuery query) {
        List<Long> values = serviceImpl.getConsultationsCountByMonth(query.getBusinessId(), query.getYear());

        return new ContConsultByYearsResponse(values);
    }
}
