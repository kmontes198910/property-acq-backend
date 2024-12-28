package com.kynsof.calendar.application.command.receipt.confirmPayment;

import com.kynsof.calendar.domain.dto.enumType.EStatusReceipt;
import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ConfirmPaymentReceiptCommand implements ICommand {

    private final UUID receiptId;
    private final UUID userId;
    private final EStatusReceipt status;
    private final String requestId;
    private final String authorizationCode;
    private final String reference;
    private final String ipAddress;
    private final String userAgent;
    public ConfirmPaymentReceiptCommand(UUID receiptId, UUID userId,
                                        EStatusReceipt status, String requestId, String authorizationCode,
                                         String reference, String ipAddress, String userAgent) {


        this.receiptId = receiptId;
        this.userId = userId;
        this.status = status;
        this.requestId = requestId;
        this.authorizationCode = authorizationCode;

        this.reference = reference;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
    }

    public static ConfirmPaymentReceiptCommand fromRequest(UUID receiptId, ConfirmPaymentReceiptRequest request,
                                                           String ipAddress, String userAgent) {
        return new ConfirmPaymentReceiptCommand(receiptId, request.getUserId(),
            request.getStatus(),request.getRequestId() ,request.getAuthorizationCode() ,
                 request.getReference(),ipAddress ,userAgent );
    }

    @Override
    public ICommandMessage getMessage() {
        return new ConfirmPaymentReceiptMessage(receiptId);
    }
}
