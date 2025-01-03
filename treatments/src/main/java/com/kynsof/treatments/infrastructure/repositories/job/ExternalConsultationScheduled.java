package com.kynsof.treatments.infrastructure.repositories.job;

import com.kynsof.share.core.domain.kafka.entity.ExternalConsultationByBusiness;
import com.kynsof.treatments.domain.dto.BusinessDto;
import com.kynsof.treatments.domain.service.IBusiness;
import com.kynsof.treatments.domain.service.IExternalConsultationService;
import com.kynsof.treatments.infrastructure.service.kafka.procedure.externalConsultationByBusiness.ProducerExternalConsultationByBusinessEventService;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ExternalConsultationScheduled {
    private final IExternalConsultationService externalConsultationService;
    private final IBusiness businessService;
    private final ProducerExternalConsultationByBusinessEventService pecbbes;

    public ExternalConsultationScheduled(IExternalConsultationService externalConsultationService,
                                         IBusiness businessService,
                                         ProducerExternalConsultationByBusinessEventService pecbbes) {
        this.externalConsultationService = externalConsultationService;
        this.businessService = businessService;
        this.pecbbes = pecbbes;
    }

    @Transactional
    @Scheduled(cron = "0 0 1 * * ?")
//    @Scheduled(fixedRateString = "${pendingpay.fixedRate.in.milliseconds}")
    public void reportExternalConsultationForBusiness() {
        List<BusinessDto> _business = this.businessService.findAll();
        Dates dates = this.getDates();

        List<ExternalConsultationByBusiness> ecbbks = new ArrayList<>();
        for (BusinessDto _b : _business) {
            Long cant = this.externalConsultationService.countConsultationsByBusinessAndDateRange(
                    _b.getId(), 
                    dates.getStartDate(), 
                    dates.getEndDate()
            );
            ecbbks.add(new ExternalConsultationByBusiness(_b.getId(), cant));
        }
        System.err.println("##################################################");
        System.err.println("CANTIDADES");
        System.err.println("CANTIDADES: " + ecbbks.get(0).getBusinessID());
        System.err.println("CANTIDADES: " + ecbbks.get(0).getCantExternalConsultation());
        System.err.println("##################################################");

        pecbbes.create(ecbbks);
    }

    private Dates getDates() {
        LocalDate currentDate = LocalDate.now();
        LocalDate startOfCurrentMonth = currentDate.withDayOfMonth(1);
        LocalDate startOfPreviousMonth = startOfCurrentMonth.minusMonths(1);
        LocalDate endOfPreviousMonth = startOfCurrentMonth.minusDays(1);

        Date startDate = Date.from(startOfPreviousMonth.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(endOfPreviousMonth.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        return new Dates(startDate, endDate);
    }
}
