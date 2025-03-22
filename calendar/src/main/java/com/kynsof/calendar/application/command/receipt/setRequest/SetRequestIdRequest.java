package com.kynsof.calendar.application.command.receipt.setRequest;

import com.kynsof.calendar.domain.dto.enumType.EStatusReceipt;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SetRequestIdRequest {
    private EStatusReceipt status;
    private String requestId;
    private String processUrl;
    private String reference;

}
