package com.kynsof.payment.domain.dto.excel.processStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class ProcessStatus {
    private final ProcessStatusEnum status;
    private final String message;
    private final String requestId;
}
