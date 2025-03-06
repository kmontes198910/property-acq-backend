package com.kynsof.payment.application.command.billing.createall;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.payment.domain.dto.BillingDto;
import com.kynsof.payment.domain.dto.BusinessDto;
import com.kynsof.payment.domain.dto.ClientDto;
import com.kynsof.payment.domain.dto.enumDto.Status;
import com.kynsof.payment.domain.dto.enumDto.TypeOperation;
import com.kynsof.payment.domain.service.IBillingService;
import com.kynsof.payment.domain.service.IBusiness;
import com.kynsof.payment.domain.service.IClientService;
import com.kynsof.payment.infrastructure.service.http.PatientHttpUUIDService;
import com.kynsof.share.core.domain.http.entity.PatientHttp;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CreateAllBillingCommandHandler implements ICommandHandler<CreateAllBillingCommand> {

    private final IBillingService serviceImpl;
    private final IClientService clientService;
    private final IBusiness businessService;
    private final PatientHttpUUIDService patientHttpUUIDService;

    public CreateAllBillingCommandHandler(IBillingService serviceImpl,
                                          IClientService clientService,
                                          IBusiness businessService,
                                          PatientHttpUUIDService patientHttpUUIDService) {

        this.serviceImpl = serviceImpl;
        this.clientService = clientService;
        this.businessService = businessService;
        this.patientHttpUUIDService = patientHttpUUIDService;
    }

    @Override
    public void handle(CreateAllBillingCommand command) {

        ClientDto clientDto = null;
        try {
            clientDto = clientService.findById(command.getClientId());
        } catch (Exception e) {
            PatientHttp patient = patientHttpUUIDService.sendGetHttpRequest(command.getClientId());
            clientDto = new ClientDto(
                    patient.getId(),
                    patient.getIdentification(),
                    patient.getName(),
                    patient.getLastName(),
                    Status.valueOf(patient.getStatus()),
                    patient.getEmail(),
                    patient.getPhone()
            );
            this.clientService.create(clientDto);
        }
        BusinessDto businessDto = businessService.findById(command.getBusinessId());
        List<BillingDto> billingDtoList = new ArrayList<>();
        for (CreateBillingPartialRequest billingPartialRequest : command.getBillingPartialRequests()) {
            BillingDto create = new BillingDto(
                    UUID.randomUUID(),
                    command.getClientId(),
                    command.getBusinessId(),
                    billingPartialRequest.getCode(),
                    billingPartialRequest.getDescription(),
                    billingPartialRequest.getStatus(),
                    command.isProforma(),
                    billingPartialRequest.getCost(),
                    command.getUserSystemId(),
                    command.getUserSystemFullName(),
                    TypeOperation.Other
            );

            create.setClient(clientDto);
            create.setBusiness(businessDto);
            billingDtoList.add(create);
        }
        this.serviceImpl.createAll(billingDtoList);
        command.setResult(true);

    }
}
