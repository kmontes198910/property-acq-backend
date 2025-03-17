package com.kynsof.payment.domain.dto.excel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class RowProcess {
    private int rowProcess;
    private double costProcess;
    private int rowNotProcess;
    private double costNotProcess;
}
