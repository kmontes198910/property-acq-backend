package com.kynsof.payment.application.command.groupPayment.createGroupPaymentUnif;

import com.kynsof.payment.domain.service.IBusiness;
import com.kynsof.payment.domain.service.IClientService;
import com.kynsof.payment.domain.service.IGroupPaymentService;
import com.kynsof.payment.application.service.http.PatientHttpUUIDService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
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
                command.getReference(),
                command.getRequestId());
        command.setId(id);
    }
}
