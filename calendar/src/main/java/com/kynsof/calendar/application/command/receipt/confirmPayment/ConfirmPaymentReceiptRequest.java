package com.kynsof.calendar.application.command.receipt.confirmPayment;

import com.kynsof.calendar.domain.dto.enumType.EStatusReceipt;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ConfirmPaymentReceiptRequest {
    private UUID userId;
    private EStatusReceipt status;
    private String requestId;
    private String authorizationCode;
    private String reference;
}
