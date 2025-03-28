package com.kynsof.calendar.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class ReceiptSummaryDTO {
    private UUID id;
    private UUID businessId;
    private String requestId;
}
