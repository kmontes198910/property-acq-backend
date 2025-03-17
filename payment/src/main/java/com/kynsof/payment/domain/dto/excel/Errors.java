package com.kynsof.payment.domain.dto.excel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Errors {
    private String rowIndex;
    private String column;
    private String value;
    private String msg;
}
