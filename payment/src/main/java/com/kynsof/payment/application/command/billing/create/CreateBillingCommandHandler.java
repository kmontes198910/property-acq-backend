package com.kynsof.payment.application.command.billing.create;

import com.kynsof.payment.domain.dto.BillingDto;
import com.kynsof.payment.domain.dto.BusinessDto;
import com.kynsof.payment.domain.dto.ClientDto;
import com.kynsof.payment.domain.dto.enumDto.BillingStatus;
import com.kynsof.payment.domain.dto.enumDto.Status;
import com.kynsof.payment.domain.service.IBillingService;
import com.kynsof.payment.domain.service.IBusiness;
import com.kynsof.payment.domain.service.IClientService;
import com.kynsof.payment.application.service.http.PatientHttpUUIDService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.http.entity.PatientHttp;
import com.kynsof.share.core.domain.response.ErrorField;
import org.springframework.stereotype.Component;

@Component
public class CreateBillingCommandHandler implements ICommandHandler<CreateBillingCommand> {

    private final IBillingService serviceImpl;
    private final IClientService clientService;
    private final IBusiness businessService;
    private final PatientHttpUUIDService patientHttpUUIDService;

    public CreateBillingCommandHandler(IBillingService serviceImpl,
                                       IClientService clientService,
                                       IBusiness businessService,
                                       PatientHttpUUIDService patientHttpUUIDService) {
        this.serviceImpl = serviceImpl;
        this.clientService = clientService;
        this.businessService = businessService;
        this.patientHttpUUIDService = patientHttpUUIDService;
    }

    @Override
    public void handle(CreateBillingCommand command) {
        if (!this.serviceImpl.existsByCodeAndBusinessIdAndStatusAndPatientId(command.getCode(), command.getBusinessId(), BillingStatus.PENDING, command.getClientId())) {
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

            BillingDto create = new BillingDto(
                    command.getId(),
                    command.getClientId(),
                    command.getBusinessId(),
                    command.getCode(),
                    command.getDescription(),
                    command.getStatus(),
                    command.isProforma(),
                    command.getCost(),
                    command.getUserSystemId(),
                    command.getUserSystemFullName(),
                    command.getTypeOperation()
            );

            create.setClient(clientDto);
            create.setBusiness(businessDto);

            this.serviceImpl.create(create);
        } else {
            throw new BusinessNotFoundException(new GlobalBusinessException(
                    DomainErrorMessage.BILLING_SERVICE_NOT_FOUND,
                    new ErrorField("code", "Ya se encuentra registrado un pago con ese código para el paciente.")));
        }
    }

}
