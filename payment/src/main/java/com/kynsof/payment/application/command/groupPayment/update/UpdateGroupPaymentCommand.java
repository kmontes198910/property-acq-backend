package com.kynsof.payment.application.command.groupPayment.update;

import com.kynsof.payment.domain.dto.enumDto.GroupPaymentStatus;
import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateGroupPaymentCommand implements ICommand {

    private  UUID id;
    private String requestId;
    private String authorizationCode;
    private String reference;
    private String processUrl;
    private GroupPaymentStatus status;
    private Boolean result;

    public UpdateGroupPaymentCommand(UUID id, UpdateGroupPaymentRequest request) {
      this.id =id;
      this.requestId = request.getRequestId();
      this.authorizationCode = request.getAuthorizationCode();
      this.reference = request.getReference();
      this.processUrl = request.getProcessUrl();
      this.status = request.getStatus();
    }


    public static UpdateGroupPaymentCommand fromRequest(UUID id,UpdateGroupPaymentRequest request) {
        return new UpdateGroupPaymentCommand(
       id,request
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateGroupPaymentMessage(result);
    }
}
