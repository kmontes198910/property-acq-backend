package com.kynsof.payment.application.command.groupPayment.createGroupPaymentUnif;

import com.kynsof.payment.domain.dto.BillingDto;
import com.kynsof.payment.domain.dto.BusinessDto;
import com.kynsof.payment.domain.dto.ClientDto;
import com.kynsof.payment.domain.dto.enumDto.BillingStatus;
import com.kynsof.payment.domain.dto.enumDto.Status;
import com.kynsof.payment.domain.service.IBusiness;
import com.kynsof.payment.domain.service.IClientService;
import com.kynsof.payment.domain.service.IGroupPaymentService;
import com.kynsof.payment.infrastructure.service.http.PatientHttpUUIDService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.http.entity.PatientHttp;
import com.kynsof.share.core.domain.response.ErrorField;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreateGroupPaymentUnifCommandHandler implements ICommandHandler<CreateGroupPaymentUnifCommand> {

    private final IGroupPaymentService serviceImpl;
    private final IClientService clientService;
    private final IBusiness businessService;
    private final PatientHttpUUIDService patientHttpUUIDService;
    public CreateGroupPaymentUnifCommandHandler(IGroupPaymentService serviceImpl, IClientService clientService, IBusiness businessService, PatientHttpUUIDService patientHttpUUIDService) {
        this.serviceImpl = serviceImpl;

        this.clientService = clientService;
        this.businessService = businessService;
        this.patientHttpUUIDService = patientHttpUUIDService;
    }

    @Override
    public void handle(CreateGroupPaymentUnifCommand command) {

//           ClientDto clientDto = null;
//            try {
//                clientDto = clientService.findById(command.getClientId());
//            } catch (Exception e) {
//                PatientHttp patient = patientHttpUUIDService.sendGetHttpRequest(command.getClientId());
//                clientDto = new ClientDto(
//                        patient.getId(),
//                        patient.getIdentification(),
//                        patient.getName(),
//                        patient.getLastName(),
//                        Status.valueOf(patient.getStatus()),
//                        patient.getEmail(),
//                        patient.getPhone()
//                );
//                this.clientService.create(clientDto);
//            }


        UUID id = this.serviceImpl.createBillingsAndGroupPayment(command.getClientId(),
                command.getBusinessId(),
                command.getBillings(),
                command.getUserSystemId(),
                command.getUserSystemFullName(),
                command.getPaymentType(),
                command.getPaymentStatus(),
                command.getInsuranceId(),
                command.getTypeOperation()
                , command.isProforma(),
                command.getAuthorizationCode(),
                command.getReference());
        command.setId(id);
    }
}
