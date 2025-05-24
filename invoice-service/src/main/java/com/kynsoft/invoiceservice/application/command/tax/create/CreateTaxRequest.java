package com.kynsoft.invoiceservice.application.command.tax.create;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;
import com.kynsoft.invoiceservice.infrastructure.entities.Tax.TaxType;

@Getter
@Setter
public class CreateTaxRequest {
    private String code;
    private String name;
    private String description;
    private BigDecimal value;
    private TaxType taxType;
    private Boolean isPredetermined;
}
