package com.kynsof.payment.domain.dto.excel.exception;

import com.kynsof.payment.domain.dto.excel.processStatus.ProcessStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BusinessNotFoundException extends RuntimeException {

    private final ProcessStatusEnum status;
    private final String message;
    private final String requestId;

    @Override
    public String getMessage() {
        return message;
    }

}
