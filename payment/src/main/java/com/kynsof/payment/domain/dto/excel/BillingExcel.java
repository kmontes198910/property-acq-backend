package com.kynsof.payment.domain.dto.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BillingExcel {
    @ExcelProperty("Identificador")
    private String identification;
    @ExcelProperty("Code")
    private String code;
    @ExcelProperty("Cost")
    private double cost;
    @ExcelProperty("Description")
    private String description;

    private int rowIndex;
}
