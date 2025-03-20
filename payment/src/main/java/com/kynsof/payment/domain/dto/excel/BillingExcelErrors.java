package com.kynsof.payment.domain.dto.excel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BillingExcelErrors {
    private String requestId;
    private RowProcess rowProcess;
    private List<Errors> error;
}
