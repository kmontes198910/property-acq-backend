package com.kynsoft.propertyacqcenter.application.command.analysis.create;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SaleValueRequest {
    private Double estimatedValue;
    private Date lastYear;
}
