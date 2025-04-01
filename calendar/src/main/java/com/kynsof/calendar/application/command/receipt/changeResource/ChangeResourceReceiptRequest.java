package com.kynsof.calendar.application.command.receipt.changeResource;

import com.kynsof.calendar.domain.dto.enumType.EStatusReceipt;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ChangeResourceReceiptRequest {
    private UUID resourceId;

}
