package com.kynsoft.cirugia.application.query.surgery.listbydaterange;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListSurgeriesByDateRangeRequest {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private UUID businessId;
}