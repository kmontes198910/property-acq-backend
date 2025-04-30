package com.kynsoft.propertyacqcenter.domain.dto.property;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class SaleHistoryDto {
    private String event;
    private LocalDateTime date;
    private double price;
}
