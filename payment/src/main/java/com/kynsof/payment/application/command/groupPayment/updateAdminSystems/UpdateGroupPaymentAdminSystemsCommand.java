package com.kynsof.payment.application.command.groupPayment.updateAdminSystems;

import com.kynsof.payment.domain.dto.enumDto.GroupPaymentStatus;
import com.kynsof.payment.domain.dto.enumDto.PaymentType;
import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateGroupPaymentAdminSystemsCommand implements ICommand {

    private  UUID id;
    private String authorizationCode;
    private String reference;
    private GroupPaymentStatus status;
    private PaymentType paymentType;
    private Boolean result;

    public UpdateGroupPaymentAdminSystemsCommand(UUID id, UpdateGroupPaymentAdminSystemsRequest request) {
      this.id =id;
      this.paymentType = request.getPaymentType();
      this.authorizationCode = request.getAuthorizationCode();
      this.reference = request.getReference();
      this.status = request.getStatus();
    }


    public static UpdateGroupPaymentAdminSystemsCommand fromRequest(UUID id, UpdateGroupPaymentAdminSystemsRequest request) {
        return new UpdateGroupPaymentAdminSystemsCommand(
       id,request
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateGroupPaymentAdminSystemsMessage(result);
    }
}
