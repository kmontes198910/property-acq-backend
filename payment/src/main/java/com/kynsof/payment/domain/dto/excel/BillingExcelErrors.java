package com.kynsof.payment.domain.dto.excel;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BillingExcelErrors {
    private String requestId;
    private RowProcess rowProcess;
    private List<Errors> error;
}
