package com.kynsof.treatments.domain.evnts;


import com.kynsof.treatments.domain.dto.BillingDto;
import com.kynsof.treatments.domain.dto.BusinessDto;
import com.kynsof.treatments.domain.dto.BusinessProcedureDto;
import com.kynsof.treatments.domain.dto.PatientDto;
import com.kynsof.treatments.domain.dto.enumDto.BillingStatus;
import com.kynsof.treatments.domain.service.IBillingService;
import com.kynsof.treatments.domain.service.IBusiness;
import com.kynsof.treatments.domain.service.IBusinessProcedureService;
import com.kynsof.treatments.domain.service.IPatientsService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class CreateBillingEventHandler {
    private final IBillingService billingService;
    private final IBusinessProcedureService businessProcedureService;
    private final IPatientsService patientsService;
    private final IBusiness businessService;


    public CreateBillingEventHandler(IBillingService billingService, IBusinessProcedureService businessService, IPatientsService patientsService, IBusiness businessService1) {

        this.billingService = billingService;
        this.businessProcedureService = businessService;
        this.patientsService = patientsService;
        this.businessService = businessService1;
    }

    @EventListener
    public void onCreateEmailListEvent(CreateBillingEvent event) {
        List<String> codeList = event.getExamenCodeList();
        BusinessDto businessDto = businessService.findById(event.getBusinessId());
        PatientDto patientDto = patientsService.findById(event.getPatientId());
        List<BusinessProcedureDto> businessProcedureDtos = businessProcedureService.findByCodes(event.getBusinessId(), codeList);

        List<BillingDto> billingDtoList = new ArrayList<>();
        for (BusinessProcedureDto businessProcedureDto : businessProcedureDtos) {
            if (!this.billingService.existsByCodeAndBusinessIdAndStatusAndPatientId(businessProcedureDto.getProcedure().getCode(), event.getBusinessId(), BillingStatus.PENDING,
                    event.getPatientId())) {
                BillingDto billingDto = new BillingDto();
                billingDto.setCode(businessProcedureDto.getProcedure().getCode());
                billingDto.setProforma(true);
                billingDto.setCost(businessProcedureDto.getPrice());
                billingDto.setStatus(BillingStatus.PENDING);
                billingDto.setDescription(businessProcedureDto.getProcedure().getDescription());
                billingDto.setBusiness(businessDto);
                billingDto.setPatient(patientDto);
                billingDtoList.add(billingDto);
            }
        }
        if (!billingDtoList.isEmpty()) {
            billingService.createAll(billingDtoList);
        }

    }
}
