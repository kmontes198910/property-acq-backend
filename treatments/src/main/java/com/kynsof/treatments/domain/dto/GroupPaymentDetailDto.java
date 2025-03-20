package com.kynsof.treatments.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupPaymentDetailDto {
    private UUID id;
    private String code;
    private String description;
    private Double cost;
    private LocalDateTime createdAt;
}
