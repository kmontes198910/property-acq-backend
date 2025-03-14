package com.kynsof.payment.application.command.groupPayment.createGroupPaymentUnif;

import com.kynsof.payment.domain.dto.enumDto.GroupPaymentStatus;
import com.kynsof.payment.domain.dto.enumDto.PaymentType;
import com.kynsof.payment.domain.dto.enumDto.TypeOperation;
import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateGroupPaymentUnifCommand implements ICommand {
    private UUID id;
    private final UUID clientId;
    private final UUID businessId;
    private final List<CreateBillingPartialRequest> billings;
    private final UUID userSystemId;
    private final String userSystemFullName;
    private final PaymentType paymentType;
    private final GroupPaymentStatus paymentStatus;
    private final String insuranceId;
    private final TypeOperation typeOperation;
    private final boolean isProforma;
    private final String authorizationCode;
    private final String reference;

    public CreateGroupPaymentUnifCommand(UUID clientId, UUID businessId, List<CreateBillingPartialRequest> billings, UUID userSystemId,
                                         String userSystemFullName, PaymentType paymentType, GroupPaymentStatus paymentStatus, String insuranceId, TypeOperation typeOperation, boolean isProforma, String authorizationCode, String reference) {

        this.clientId = clientId;
        this.businessId = businessId;
        this.billings = billings;
        this.userSystemId = userSystemId;
        this.userSystemFullName = userSystemFullName;
        this.paymentType = paymentType;
        this.paymentStatus = paymentStatus;
        this.insuranceId = insuranceId;
        this.typeOperation = typeOperation;
        this.isProforma = isProforma;
        this.authorizationCode = authorizationCode;
        this.reference = reference;
    }


    public static CreateGroupPaymentUnifCommand fromRequest(CreateGroupPaymentUnifRequest request) {
        return new CreateGroupPaymentUnifCommand(
             request.getClientId(),
                request.getBusinessId(),
                request.getBillings(),
                request.getUserSystemId(),
                request.getUserSystemFullName(),
                request.getPaymentType(),
                request.getPaymentStatus(),
                request.getInsuranceId(),
                request.getTypeOperation()
                , request.isProforma(),
                request.getAuthorizationCode(),
                request.getReference());
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateGroupPaymentUnifMessage(id);
    }
}
